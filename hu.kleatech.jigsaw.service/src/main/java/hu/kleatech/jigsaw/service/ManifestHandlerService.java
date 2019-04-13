package hu.kleatech.jigsaw.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.kleatech.jigsaw.model.*;
import hu.kleatech.jigsaw.service.serialization.*;
import static hu.kleatech.jigsaw.utils.Constants.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

@Service
public class ManifestHandlerService extends hu.kleatech.jigsaw.service.interfaces.ManifestHandlerService {

    @Autowired EventGroupService eventGroupService;
    @Autowired EventService eventService;
    @Autowired CompetitionService competitionService;
    
    @Override
    public void loadTemplates(String eventGroupName) throws IOException {
        loadManifest(eventGroupName);
        copyTemplates(eventGroupName);
    }
    
    @Override
    public synchronized void copyTemplates(String eventGroupName) throws IOException {
        Arrays.stream(USER_DIR.resolve(MODULES_DIR_NAME).resolve(LOADED_MODULES_DIR_NAME).toFile().listFiles()).forEach(File::delete);
        for(File f : USER_DIR.resolve(MODULES_DIR_NAME).resolve(eventGroupName).toFile().listFiles()) {
            Path target = USER_DIR.resolve(MODULES_DIR_NAME).resolve(LOADED_MODULES_DIR_NAME).resolve(f.getName());
            if (f.getName().endsWith("html")) Files.copy(f.toPath(), target);
        }
    }
    
    @Override
    public void loadManifest(String eventGroupName) throws IOException {
        Manifest manifest = parseManifest(eventGroupName);
        EventGroup eventGroup = loadEventGroup(eventGroupName, manifest.getTemplate());
        for(Events e : manifest.getEvents()) {
            Event event = eventService.add(eventGroup, e.getName(), e.getTemplate(), null);
            for(Competitions c : e.getCompetitions()) {
                competitionService.add(event, c.getName(), c.getTemplate(), null);
            }
        }
    }
    
    @Override
    public List<String> findManifests() {
        return Arrays.stream(USER_DIR.resolve(MODULES_DIR_NAME).toFile().listFiles(File::isDirectory)).map(File::getName).filter(d -> !d.equals(LOADED_MODULES_DIR_NAME)).collect(Collectors.toList());
    }
    
    private volatile boolean fileSystemWatchRunning = false;
    @EventListener(ApplicationReadyEvent.class)
    @Order(7)
    @Override
    public void startFileSystemWatch() throws IOException {
        if (fileSystemWatchRunning) return;
        WatchService watchService = FileSystems.getDefault().newWatchService();
        USER_DIR.resolve(MODULES_DIR_NAME).register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
        new Thread(() -> {
            fileSystemWatchRunning = true;
            WatchKey key;
            try {
                while ((key = watchService.take()) != null) {
                    key.pollEvents();
                    refreshNow();
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                fileSystemWatchRunning = false;
            }
        }, "WatchService").start();
    }

    private final LinkedList<DeferredResult<String>> registeredListeners = new LinkedList<>();
    @Override
    public void registerListener(DeferredResult<String> listener) {
        registeredListeners.add(listener);
    }    
    
    @EventListener(ApplicationReadyEvent.class)
    @Order(5)
    @Override
    public void refreshNow() throws IOException {
        List<String> manifests = findManifests();
        for(String m : manifests) loadManifest(m);
        System.out.println("refreshed");
        registeredListeners.forEach(l -> l.setResult("EVENT"));
    }
    
    private Manifest parseManifest(String eventGroupName) throws IOException {
        return new ObjectMapper().readValue(USER_DIR.resolve(MODULES_DIR_NAME).resolve(eventGroupName).resolve("manifest.json").toFile(), Manifest.class);
    }
    
    private EventGroup loadEventGroup(String eventGroupName, String template) {
        EventGroup eventGroup;
        if (eventGroupService.getAll().stream().noneMatch(eg -> eg.getName().equals(eventGroupName)))
             eventGroup = eventGroupService.add(eventGroupName, template, null);
        else eventGroup = eventGroupService.getAll().stream().filter(eg -> eg.getName().equals(eventGroupName)).findFirst().get();
        return eventGroup;
    }
}
