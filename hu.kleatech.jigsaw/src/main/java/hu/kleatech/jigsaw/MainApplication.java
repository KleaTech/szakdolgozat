package hu.kleatech.jigsaw;

import hu.kleatech.jigsaw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import hu.kleatech.jigsaw.service.interfaces.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import static hu.kleatech.jigsaw.utils.Constants.USER_DIR;
import hu.kleatech.jigsaw.scripting.EngineProvider;

@SpringBootApplication
@Configuration
public class MainApplication implements ApplicationRunner {
    
    @Autowired SpringTemplateEngine templateEngine;
    @EventListener(ApplicationStartedEvent.class)
    public void load() {
        EngineProvider.load();
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

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Team team1 = teamService.add("Avasi", "teamFragment_generated", null);
			participantService.add("Példa Lajos", LocalDate.now().minusYears(18), Sex.NOT_GIVEN, team1, "participantFragment_generated", null);
		Team team2 = teamService.add("Diósgyőri", "teamFragment_generated", null);
			participantService.add("Kovács István", LocalDate.now().minusYears(20), Sex.MALE, team2, "participantFragment_generated", null);
			participantService.add("Tóth Noémi", LocalDate.now().minusYears(21), Sex.FEMALE, team2, "participantFragment_generated", null);
                Team team3 = teamService.add("Jezsuita", "teamFragment_generated", null);
                        participantService.add("Kovács Mátyás", LocalDate.now().minusYears(17), Sex.MALE, team3, "participantFragment_generated", null);
                        participantService.add("Fazekas Ibolya", LocalDate.now().minusYears(18), Sex.FEMALE, team3, "participantFragment_generated", null);
		EventGroup eventGroup = eventGroupService.add("Diákolimpia", "eventGroupFragment_generated", null);
			Event girl = eventService.add(eventGroup, "Lány", "eventFragment_generated", null);
				competitionService.add(girl, "competition", "competitionFragment_default", null);
			Event boy = eventService.add(eventGroup, "Fiú", "eventFragment_generated", null);
				competitionService.add(boy, "Talaj", "competitionFragmentOther_generated", null);
				competitionService.add(boy, "Távolugrás", "competitionFragmentJump_generated", null);
                competitionService.add(boy, "Gyűrű", "competitionFragmentOther_generated", null);
				competitionService.add(boy, "Ló", "competitionFragmentOther_generated", null);
				competitionService.add(boy, "Korlát", "competitionFragmentOther_generated", null);
				competitionService.add(boy, "Nyújtó", "competitionFragmentOther_generated", null);
	}
}
