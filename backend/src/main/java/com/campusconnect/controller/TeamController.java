package com.campusconnect.controller;

import com.campusconnect.entity.Team;
import com.campusconnect.entity.User;
import com.campusconnect.service.TeamService;
import com.campusconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listTeams(@RequestParam(value = "query", required = false) String query,
                            @RequestParam(value = "status", required = false) String status,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model) {

        User currentUser = userDetails != null ? userService.findByEmail(userDetails.getUsername()).orElse(null) : null;
        List<Team> teams = teamService.getAllTeams(query, status);

        model.addAttribute("teams", teams);
        model.addAttribute("query", query);
        model.addAttribute("status", status);
        model.addAttribute("currentUser", currentUser);
        return "teams/list";
    }

    @GetMapping("/create")
    public String showCreateTeamForm(Model model) {
        model.addAttribute("team", new Team());
        return "teams/create";
    }

    @PostMapping("/create")
    public String handleCreateTeam(@AuthenticationPrincipal UserDetails userDetails,
                                   @RequestParam("name") String name,
                                   @RequestParam("projectTitle") String projectTitle,
                                   @RequestParam("description") String description,
                                   @RequestParam("requiredSkills") String requiredSkills,
                                   @RequestParam("targetMemberCount") int targetMemberCount,
                                   @RequestParam("deadline") String deadline,
                                   RedirectAttributes redirectAttributes) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser == null) return "redirect:/login";

        Team team = teamService.createTeam(currentUser, name, projectTitle, description, requiredSkills, targetMemberCount, deadline);
        redirectAttributes.addFlashAttribute("successMessage", "Team post created! Members can now request to join.");
        return "redirect:/workspace/" + team.getId();
    }

    @PostMapping("/{id}/join")
    public String handleJoinRequest(@PathVariable("id") Long id,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    @RequestParam("message") String message,
                                    RedirectAttributes redirectAttributes) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser == null) return "redirect:/login";

        boolean requested = teamService.requestToJoin(id, currentUser, message);
        if (requested) {
            redirectAttributes.addFlashAttribute("successMessage", "Join request sent to the team leader!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not send join request (already a member or request pending).");
        }
        return "redirect:/teams";
    }

    @PostMapping("/request/{requestId}/respond")
    public String handleRespondToRequest(@PathVariable("requestId") Long requestId,
                                         @RequestParam("accept") boolean accept,
                                         @RequestParam("teamId") Long teamId,
                                         @AuthenticationPrincipal UserDetails userDetails,
                                         RedirectAttributes redirectAttributes) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser != null) {
            teamService.handleJoinRequest(requestId, currentUser, accept);
            redirectAttributes.addFlashAttribute("successMessage", accept ? "Request accepted!" : "Request rejected.");
        }
        return "redirect:/workspace/" + teamId;
    }
}
