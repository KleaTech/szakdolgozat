package hu.kleatech.jigsaw.controller;

import hu.kleatech.jigsaw.model.EventGroup;
import hu.kleatech.jigsaw.model.Participant;
import hu.kleatech.jigsaw.service.interfaces.EventGroupService;
import hu.kleatech.jigsaw.service.interfaces.ParticipantService;
import hu.kleatech.jigsaw.service.interfaces.TeamService;
import hu.kleatech.jigsaw.utils.StaticMap;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TeamController {
    
    @Autowired TeamService teamService;
    @Autowired ParticipantService participantService;
    @Autowired EventGroupService eventGroupService;
    
    @GetMapping("/getEventGroup")
    public String getEventGroup(Model model) {
        model.addAttribute("eventGroups", eventGroupService.getAll());
        model.addAttribute("pojo", new ObjectTransferHandler.ParticipantDTO());
        return "eventGroup.html :: fragment";
    }
    
    @GetMapping("/getTeamsFragment/{eventGroupId}")
    public String getTeamsFragment(Model model, @PathVariable Long eventGroupId) {
        model.addAttribute("eventGroups", eventGroupService.getAll());
        model.addAttribute("teams", eventGroupService.get(eventGroupId).getAssociatedTeams());
        model.addAttribute("pojo", new ObjectTransferHandler.ParticipantDTO());
        return "teams.html :: fragment";
    }
    
    @PostMapping("/addTeam/{associatedEventGroupId}")
    @ResponseBody
    public String addTeam(Model model, @RequestParam String name, @PathVariable Long associatedEventGroupId) {
        teamService.add(name, "teamFragment_generated", eventGroupService.get(associatedEventGroupId), null);
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
