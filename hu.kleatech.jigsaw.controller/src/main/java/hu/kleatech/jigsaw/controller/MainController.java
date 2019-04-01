package hu.kleatech.jigsaw.controller;

import hu.kleatech.jigsaw.model.*;
import hu.kleatech.jigsaw.service.interfaces.*;
import hu.kleatech.jigsaw.utils.StaticMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import hu.kleatech.jigsaw.api.Dispatcher;
import hu.kleatech.jigsaw.api.EngineProvider;
import static hu.kleatech.jigsaw.utils.Utils.*;
import java.util.*;
import static hu.kleatech.jigsaw.controller.ControllerUtils.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;

@Controller
@Transactional
public class MainController {

    @Autowired EventGroupService eventGroupService;
    @Autowired EventService eventService;
    @Autowired RoundService roundService;
    @Autowired CompetitionService competitionService;
    @Autowired ParticipantService participantService;
    @Autowired TeamService teamService;
    EngineProvider engineProvider;

    @org.springframework.context.event.EventListener(ApplicationReadyEvent.class)
    public void load() {
        engineProvider = Dispatcher.getEngineProvider();
    }
    
    @RequestMapping("/")
    public String index(Model model, Locale locale) {
        model.addAttribute("username", "Béla");
        model.addAttribute("datetime", new Date());
        model.addAttribute("eventGroups", eventGroupService.getAll());
        return "index";
    }
    
    @GetMapping("/getHomeFragment")
    public String getHomeFragment(Model model, Locale locale) {
        index(model, locale);
        return "fragments.html :: home";
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
        model.addAttribute("prefunc", TryOrNull(() -> engineProvider.getEngine(scriptPath(compSelected)).preresults(scriptName(compSelected, ResultType.PRERESULT))));
        model.addAttribute("func", TryOrNull(() -> engineProvider.getEngine(scriptPath(compSelected)).result(scriptName(compSelected, ResultType.RESULT))));
        return compSelected.getTemplate() + " :: fragment";
    }
    @PostMapping("/addRound/{compId}")
    @ResponseBody
    public String addRound(@ModelAttribute StaticMap<String> request, @PathVariable String compId, Model model) {
        Competition comp = competitionService.get(Long.parseLong(compId));
        Round round = new Round(participantService.getAll().get(0), comp, null);
        request.stream().filter(e -> !e.trim().isEmpty()).mapToDouble(Double::parseDouble).forEachOrdered(round::add);
        roundService.add(round);
        return "SUCCESS";
    }
    @PostMapping("/editRound/{id}")
    @ResponseBody
    public String editRound(@ModelAttribute StaticMap<String> request, @PathVariable String id, Model model) {
        Round old = roundService.get(Long.parseLong(id));
        Round nevv = new Round(participantService.getAll().get(0), old.getCompetition(), null);
        request.stream().filter(e -> !e.trim().isEmpty()).mapToDouble(Double::parseDouble).forEachOrdered(nevv::add);
        roundService.replace(old, nevv);
        return "SUCCESS";
    }
}
