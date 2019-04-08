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
import java.util.stream.Collectors;
import org.springframework.boot.context.event.ApplicationReadyEvent;

@Controller
@Transactional
@SessionAttributes({"eventSelected", "teamSelected"})
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
    public String getEventGroupFragment(Model model, @PathVariable("eventGroupId") Long eventGroupId) {
        EventGroup eventGroupSelected = eventGroupService.get(eventGroupId);
        model.addAttribute("eventGroupSelected", eventGroupSelected);
        return eventGroupSelected.getTemplate() + " :: fragment";
    }
    
    //@ModelAttribute("eventSelected")
    //public Long eventSelectedSessionScopedAttribute(){ return null; }
    
    @GetMapping("/getEventFragment/{eventId}")
    public String getEventFragment(Model model, @PathVariable("eventId") Long eventId) {
        Event eventSelected = eventService.get(eventId);
        model.addAttribute("eventSelected", eventSelected);
        model.addAttribute("teams", teamService.getAll());
        return eventSelected.getTemplate() + " :: fragment";
    }
    @GetMapping("/getCompetitionFragment/{compId}")
    public String getCompetitionFragment(Model model, @PathVariable("compId") Long compId) {
        Competition compSelected = competitionService.get(compId);
        model.addAttribute("compSelected", compSelected);
        Team old = (Team)model.asMap().get("teamSelected");
        Team nevv = teamService.get(old);
        model.addAttribute("teamSelected", nevv);
        model.addAttribute("actualRounds", compSelected.getRounds().stream().filter(r -> r.getParticipant().getTeam().equals(nevv)).collect(Collectors.toList()));
        model.addAttribute("pojo", new StaticMap<String>());
        model.addAttribute("prefunc", TryOrNull(() -> engineProvider.getEngine(scriptPath(compSelected)).preresults(scriptName(compSelected, ResultType.PRERESULT))));
        model.addAttribute("func", TryOrNull(() -> engineProvider.getEngine(scriptPath(compSelected)).result(scriptName(compSelected, ResultType.RESULT))));
        return compSelected.getTemplate() + " :: fragment";
    }
    @GetMapping("/getTeamFragment/{teamId}")
    public String getTeamFragment(Model model, @PathVariable("teamId") Long teamId) {
        Team teamSelected = teamService.get(teamId);
        model.addAttribute("teamSelected", teamSelected);
        return teamSelected.getTemplate() + " :: fragment";
    }
    @PostMapping("/addRound/{compId}/{partId}")
    @ResponseBody
    public String addRound(@ModelAttribute StaticMap<String> request, @PathVariable("compId") Long compId, @PathVariable("partId") Long partId,  Model model) {
        Competition comp = competitionService.get(compId);
        Round round = new Round(participantService.get(partId), comp, null);
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
    @GetMapping("/deleteRound/{id}")
    @ResponseBody
    public String deleteRound(@PathVariable Long id, Model model) {
        roundService.delete(roundService.get(id));
        return "SUCCESS";
    }
}
