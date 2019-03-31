package hu.kleatech.jigsaw.controller;

import hu.kleatech.jigsaw.model.Participant;
import hu.kleatech.jigsaw.service.interfaces.ParticipantService;
import hu.kleatech.jigsaw.service.interfaces.TeamService;
import hu.kleatech.jigsaw.utils.StaticMap;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TeamController {
    
    @Autowired TeamService teamService;
    @Autowired ParticipantService participantService;
    
    @GetMapping("/getTeamsFragment")
    public String getTeamsFragment(Model model, Locale locale) {
        model.addAttribute("teams", teamService.getAll());
        model.addAttribute("pojo", new StaticMap<String>());
        model.addAttribute("utils", ViewUtils.VIEWUTILS);
        model.addAttribute("pojo", new ObjectTransferHandler.ParticipantDTO());
        return "teams.html :: fragment";
    }
    
    @PostMapping("/addTeam")
    @ResponseBody
    public String addTeam(Model model, @RequestParam String name) {
        teamService.add(name, null);
        return "SUCCESS";
    }
    
    @PostMapping("/addParticipant/{teamId}")
    @ResponseBody
    public String addParticipant(@PathVariable("teamId") Long teamId, @ModelAttribute ObjectTransferHandler.ParticipantDTO request, Model model) {
        participantService.add(request.toEntity(teamService.get(teamId)));
        return "SUCCESS";
    }
    
    @GetMapping("/deleteTeam/{id}")
    @ResponseBody
    public String deleteTeam(Model model, @PathVariable("id") Long id) {
        teamService.delete(teamService.get(id));
        return "SUCCESS";
    }
    
    @GetMapping("/deleteParticipant/{id}")
    @ResponseBody
    public String deleteParticipant(@PathVariable("id") Long id, Model model) {
        participantService.delete(participantService.get(id));
        return "SUCCESS";
    }
}
