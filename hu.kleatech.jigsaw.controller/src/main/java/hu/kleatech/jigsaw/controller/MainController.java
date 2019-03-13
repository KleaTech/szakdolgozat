package hu.kleatech.jigsaw.controller;

import hu.kleatech.jigsaw.model.*;
import hu.kleatech.jigsaw.service.interfaces.*;
import hu.kleatech.jigsaw.utils.StaticMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import hu.kleatech.jigsaw.scripting.Engine;
import static hu.kleatech.jigsaw.utils.Utils.*;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

@Controller
@Transactional
public class MainController {

    @Autowired EventGroupService eventGroupService;
    @Autowired EventService eventService;
    @Autowired RoundService roundService;
    @Autowired CompetitionService competitionService;
    @Autowired ParticipantService participantService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStarted() {
        Competition competition = eventGroupService.getAll().get(0).getEvents().get(1).getCompetitions().get(2);
        Round round = new Round(participantService.getAll().get(0), competition, null);
        round.add(1.5, 1.7, 1.5, 2.1, 2.0, 1.6);
        roundService.add(round);
        Function<List<Double>, List<Double>> pre = TryOrNull(() -> new Engine(scriptPath("Diákolimpia")).preresults("competitionFragmentJump_generated.html".replaceFirst("[.][^.]+$", "") + "_pre.js"));
        if (pre == null) { System.out.println("script is null"); return;}
        System.out.println(pre.apply(Arrays.asList(1.0, 2.0)));
    }

    private Path scriptPath(String eventGroup) {
        return relativePath("modules", eventGroup);
    }

    @RequestMapping("/")
    public String index(Model model, Locale locale) {
        model.addAttribute("username", "Béla");
        model.addAttribute("datetime", new Date());
        model.addAttribute("eventGroups", eventGroupService.getAll());
        return "index";
    }

    @GetMapping("/getEventGroupFragment/{eventGroupId}")
    public String getEventGroupFragment(Model model, @PathVariable("eventGroupId") String eventGroupId) {
        EventGroup eventGroupSelected = eventGroupService.get(Long.parseLong(eventGroupId));
        model.addAttribute("eventGroupSelected", eventGroupSelected.getEvents());
        return eventGroupSelected.getTemplate() + " :: fragment";
    }
    @GetMapping("/getEventFragment/{eventId}")
    public String getEventFragment(Model model, @PathVariable("eventId") String eventId) {
        Event eventSelected = eventService.get(Long.parseLong(eventId));
        model.addAttribute("eventSelected", eventSelected.getCompetitions());
        return eventSelected.getTemplate() + " :: fragment";
    }
    @GetMapping("/getCompetitionFragment/{compId}")
    public String getCompetitionFragment(Model model, @PathVariable("compId") String compId) {
        Competition compSelected = competitionService.get(Long.parseLong(compId));
        model.addAttribute("compSelected", compSelected);
        model.addAttribute("pojo", new StaticMap<String>());
        model.addAttribute("prefunc", null);
        return compSelected.getTemplate() + " :: fragment";
    }
    @PostMapping("/addRound/{id}")
    @ResponseBody
    public String addRound(@ModelAttribute StaticMap<String> request, @PathVariable String id, Model model) {
        Competition comp = competitionService.get(Long.parseLong(id));
        Round round = new Round(participantService.getAll().get(0), comp, null);
        request.stream().filter(Objects::nonNull).filter(e -> !e.trim().isEmpty()).mapToDouble(Double::parseDouble).forEachOrdered(round::add);
        roundService.add(round);
        return "SUCCESS";
    }
}
