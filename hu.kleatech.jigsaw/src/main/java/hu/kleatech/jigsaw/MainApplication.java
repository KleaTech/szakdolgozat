package hu.kleatech.jigsaw;

import hu.kleatech.jigsaw.model.*;
import hu.kleatech.jigsaw.scripting.SecureEngineProvider;
import hu.kleatech.jigsaw.service.interfaces.*;
import static hu.kleatech.jigsaw.utils.Constants.*;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

@SpringBootApplication
@Configuration
public class MainApplication implements ApplicationRunner {
    
    @Autowired SpringTemplateEngine templateEngine;
    @EventListener(ApplicationStartedEvent.class)
    public void load() {
        SecureEngineProvider.load();
        setModuleTemplateResolver();
    }
    
    private void setModuleTemplateResolver() {
        FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
        fileTemplateResolver.setPrefix(USER_DIR
                .resolve("modules")
                .resolve("loadedTemplates")
                .toString() + File.separator);
        System.out.println(fileTemplateResolver.getPrefix());
        if (!Files.exists(USER_DIR
                .resolve("modules")
                .resolve("loadedTemplates")
                .resolve("eventGroupFragment_generated.html")))
            System.out.println("Template not exists");
        fileTemplateResolver.setSuffix(".html");
        fileTemplateResolver.setTemplateMode(TemplateMode.HTML);
        fileTemplateResolver.setCharacterEncoding("UTF-8");
        fileTemplateResolver.setCacheable(false);
        fileTemplateResolver.setOrder(0);
        fileTemplateResolver.setCheckExistence(true);
        templateEngine.addTemplateResolver(fileTemplateResolver);
    }

    public static void main(String[] args) {
            SpringApplication.run(MainApplication.class, args);
    }

    //TEST DATA
    @Autowired CompetitionService competitionService;
    @Autowired EventGroupService eventGroupService;
    @Autowired EventService eventService;
    @Autowired ParticipantService participantService;
    @Autowired RoundService roundService;
    @Autowired TeamService teamService;
    @Autowired ManifestHandlerService manifestHandlerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //System.out.println(Dispatcher.getEngineProvider().getEngine(USER_DIR.resolve(MODULES_DIR_NAME).resolve("Diákolimpia")).preresults("competitionFragmentJump_generated_pre.js").apply(List.of(1d,2d,3d,4d,5d,6d)));
        //System.out.println(Dispatcher.getEngineProvider().getEngine(USER_DIR.resolve(MODULES_DIR_NAME).resolve("Diákolimpia")).preresults("competitionFragmentOther_generated.js").apply(List.of(1d,2d,3d)));
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(10)
    void runAtReady() {
        EventGroup eventGroup = eventGroupService.getAll().get(0);
            Team team1 = teamService.add("Avasi", TEAM_FRAGMENT, eventGroup, null);
                    participantService.add("Példa Lajos", LocalDate.now().minusYears(18), Sex.NOT_GIVEN, team1, null);
            Team team2 = teamService.add("Diósgyőri", TEAM_FRAGMENT, eventGroup, null);
                    participantService.add("Kovács István", LocalDate.now().minusYears(20), Sex.MALE, team2, null);
                    participantService.add("Tóth Noémi", LocalDate.now().minusYears(21), Sex.FEMALE, team2, null);
            Team team3 = teamService.add("Jezsuita", TEAM_FRAGMENT, eventGroup, null);
                    participantService.add("Kovács Mátyás", LocalDate.now().minusYears(17), Sex.MALE, team3, null);
                    participantService.add("Fazekas Ibolya", LocalDate.now().minusYears(18), Sex.FEMALE, team3, null);
        //System.out.println(((SecureEngine)Dispatcher.getEngineProvider().getEngine(USER_DIR.resolve(MODULES_DIR_NAME).resolve("Diákolimpia"))).test());
    }
}
