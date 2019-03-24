package hu.kleatech.jigsaw.controller;

import hu.kleatech.jigsaw.model.*;
import hu.kleatech.jigsaw.service.interfaces.*;
import hu.kleatech.jigsaw.utils.StaticMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import hu.kleatech.jigsaw.scripting.Engine;
import static hu.kleatech.jigsaw.utils.Utils.*;
import java.util.*;
import static hu.kleatech.jigsaw.controller.ControllerUtils.*;

@Controller
@Transactional
public class MainController {

    @Autowired EventGroupService eventGroupService;
    @Autowired EventService eventService;
    @Autowired RoundService roundService;
    @Autowired CompetitionService competitionService;
    @Autowired ParticipantService participantService;
    @Autowired TeamService teamService;

    @RequestMapping("/")
    public String index(Model model, Locale locale) {
        model.addAttribute("username", "Béla");
        model.addAttribute("datetime", new Date());
        model.addAttribute("eventGroups", eventGroupService.getAll());
        model.addAttribute("utils", ViewUtils.VIEWUTILS);
        return "index";
    }
    
    @GetMapping("/getHomeFragment")
    public String getHomeFragment(Model model, Locale locale) {
        index(model, locale);
        return "fragments.html :: home";
    }
    
    @GetMapping("/getTeamsFragment")
    public String getTeamsFragment(Model model, Locale locale) {
        index(model, locale);
        model.addAttribute("teams", teamService.getAll());
        return "teams.html :: fragment";
    }
    
    @PostMapping("/addTeam/{name}")
    @ResponseBody
    public String addTeam(Model model, @PathVariable("name") String name) {
        teamService.add(name, null);
        return "SUCCESS";
    }
    
    @PostMapping("/addParticipant")
    @ResponseBody
    public String addParticipant(@ModelAttribute Participant request, Model model) {
        participantService.add(request);
        return "SUCCESS";
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
        model.addAttribute("prefunc", TryOrNull(() -> new Engine(scriptPath(compSelected)).preresults(scriptName(compSelected, ResultType.PRERESULT))));
        model.addAttribute("func", TryOrNull(() -> new Engine(scriptPath(compSelected)).result(scriptName(compSelected, ResultType.RESULT))));
        return compSelected.getTemplate() + " :: fragment";
    }
    @PostMapping("/addRound/{id}")
    @ResponseBody
    public String addRound(@ModelAttribute StaticMap<String> request, @PathVariable String id, Model model) {
        Competition comp = competitionService.get(Long.parseLong(id));
        Round round = new Round(participantService.getAll().get(0), comp, null);
        request.stream().filter(e -> !e.trim().isEmpty()).mapToDouble(Double::parseDouble).forEachOrdered(round::add);
        roundService.add(round);
        return "SUCCESS";
    }
}
