package hu.kleatech.jigsaw;

import hu.kleatech.jigsaw.model.Round;
import hu.kleatech.jigsaw.model.Sex;
import hu.kleatech.jigsaw.scripting.*;
import hu.kleatech.jigsaw.service.interfaces.CompetitionService;
import hu.kleatech.jigsaw.service.interfaces.ParticipantService;
import hu.kleatech.jigsaw.service.interfaces.RoundService;
import static hu.kleatech.jigsaw.utils.Constants.*;
import static hu.kleatech.jigsaw.utils.Utils.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }

    @Autowired ParticipantService participantService;
    @Autowired CompetitionService competitionService;
    @Autowired RoundService roundService;
    
    @EventListener(ApplicationReadyEvent.class)
    @Order(10)
    void runAtReady() {
        Try(() -> {
            var source = USER_DIR.resolve("data.sql");
            Files.move(source, source.resolveSibling("loaded_data.sql"));
        });
        //Test data can be added to the database here
    }
}
