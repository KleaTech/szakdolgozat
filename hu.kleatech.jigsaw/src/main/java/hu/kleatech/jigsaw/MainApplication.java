package hu.kleatech.jigsaw;

import hu.kleatech.jigsaw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import hu.kleatech.jigsaw.service.interfaces.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;

@SpringBootApplication
@Configuration
public class MainApplication implements ApplicationRunner {

    @Autowired SpringTemplateEngine templateEngine;
    @EventListener(ApplicationStartedEvent.class)
    public void setModuleTemplateResolver() {
        FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
        fileTemplateResolver.setPrefix(Paths.get(System.getProperty("user.dir"))
                .resolve("modules")
                .resolve("loadedTemplates")
                .toString() + File.separator);
        System.out.println(fileTemplateResolver.getPrefix());
        if (!Files.exists(Paths.get(System.getProperty("user.dir"))
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
		Team team1 = teamService.add("team1", null);
			participantService.add("participant", LocalDate.now().minusYears(18), Sex.NOT_GIVEN, team1, null);
		Team team2 = teamService.add("team2", null);
			participantService.add("participant1", LocalDate.now().minusYears(20), Sex.MALE, team2, null);
			participantService.add("participant2", LocalDate.now().minusYears(21), Sex.FEMALE, team2, null);
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
