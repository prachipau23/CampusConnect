package com.campusconnect.controller;

import com.campusconnect.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/views")
@RequiredArgsConstructor
public class JspViewController {

    private final HackathonService hackathonService;
    private final InternshipService internshipService;
    private final ProjectService projectService;
    private final CircleService circleService;
    private final TeamService teamService;
    private final NotificationService notificationService;
    private final ResourceService resourceService;
    private final ProfileService profileService;

    @GetMapping("/hackathons")
    public String hackathons(Model model) {
        model.addAttribute("hackathons", hackathonService.getAll());
        return "hackathons";
    }

    @GetMapping("/internships")
    public String internships(Model model) {
        model.addAttribute("internships", internshipService.getAll());
        return "internships";
    }

    @GetMapping("/projects")
    public String projects(Model model, @RequestParam(required = false) String q) {
        model.addAttribute("projects", projectService.search(q));
        return "projects";
    }

    @GetMapping("/circles")
    public String circles(Model model) {
        model.addAttribute("circles", circleService.getAll());
        return "circles";
    }

    @GetMapping("/teams")
    public String teams(Model model) {
        model.addAttribute("teams", teamService.getAll());
        return "teams";
    }

    @GetMapping("/notifications")
    public String notifications(Model model, Authentication auth) {
        if (auth != null) {
            model.addAttribute("notifications", notificationService.getForUser(auth.getName()));
        }
        return "notifications";
    }

    @GetMapping("/resources")
    public String resources(Model model) {
        model.addAttribute("resources", resourceService.getAll());
        return "resources";
    }

    @GetMapping("/directory")
    public String directory(Model model, @RequestParam(required = false) String q) {
        model.addAttribute("profiles", profileService.search(q));
        return "directory";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication auth) {
        if (auth != null) {
            try {
                var user = profileService.getUserByEmail(auth.getName());
                model.addAttribute("profile", profileService.getByUserId(user.getId()));
            } catch (Exception ignored) {}
        }
        return "profile";
    }

    @GetMapping("/teams/{id}/workspace")
    public String workspace(@PathVariable Long id, Model model) {
        model.addAttribute("team", teamService.getById(id));
        model.addAttribute("posts", teamService.getWorkspacePosts(id));
        return "workspace";
    }
}
