package hu.kleatech.jigsaw.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import static hu.kleatech.jigsaw.utils.Constants.*;
import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;
import hu.kleatech.jigsaw.service.serialization.Manifest;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManifestHandlerService extends hu.kleatech.jigsaw.service.interfaces.ManifestHandlerService {

    @Autowired EventGroupService eventGroupService;
    @Autowired 
    
    @Override
    public void loadManifest(String eventGroupName) throws IOException {
        Manifest manifest = new ObjectMapper().readValue(USER_DIR.resolve(MODULES_DIR_NAME).resolve(eventGroupName).resolve("manifest.json").toFile(), Manifest.class);
        eventGroupService.add(manifest.getEventGroup(), manifest.getTemplate(), null);
    }

    @Override
    public List<String> findManifests() {
        return Arrays.stream(USER_DIR.resolve(MODULES_DIR_NAME).resolve(LOADED_MODULES_DIR_NAME).toFile().listFiles(File::isDirectory)).map(File::getParent).collect(Collectors.toList());
    }
}
