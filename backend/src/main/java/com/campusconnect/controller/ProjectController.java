package com.campusconnect.controller;

import com.campusconnect.entity.Project;
import com.campusconnect.entity.User;
import com.campusconnect.service.ProjectService;
import com.campusconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listProjects(@RequestParam(value = "query", required = false) String query,
                               @RequestParam(value = "status", required = false) String status,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model) {

        User currentUser = userDetails != null ? userService.findByEmail(userDetails.getUsername()).orElse(null) : null;
        List<Project> projects = projectService.getAllProjects(query, status);

        model.addAttribute("projects", projects);
        model.addAttribute("query", query);
        model.addAttribute("status", status);
        model.addAttribute("currentUser", currentUser);
        return "projects/list";
    }

    @GetMapping("/{id}")
    public String projectDetail(@PathVariable("id") Long id,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {

        User currentUser = userDetails != null ? userService.findByEmail(userDetails.getUsername()).orElse(null) : null;
        Project project = projectService.getProjectById(id);
        if (project == null) return "redirect:/projects";

        boolean isOwner = currentUser != null && (currentUser.getId().equals(project.getOwner().getId()) || "ROLE_ADMIN".equals(currentUser.getRole()));

        model.addAttribute("project", project);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isOwner", isOwner);
        return "projects/detail";
    }

    @GetMapping("/new")
    public String newProjectForm(Model model) {
        model.addAttribute("project", new Project());
        return "projects/form";
    }

    @PostMapping("/new")
    public String handleCreateProject(@AuthenticationPrincipal UserDetails userDetails,
                                      @RequestParam("title") String title,
                                      @RequestParam("description") String description,
                                      @RequestParam("techUsed") String techUsed,
                                      @RequestParam("githubRepo") String githubRepo,
                                      @RequestParam("demoUrl") String demoUrl,
                                      @RequestParam("status") String status,
                                      @RequestParam(value = "screenshot", required = false) MultipartFile screenshot,
                                      RedirectAttributes redirectAttributes) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser == null) return "redirect:/login";

        try {
            projectService.createProject(currentUser, title, description, techUsed, githubRepo, demoUrl, status, screenshot);
            redirectAttributes.addFlashAttribute("successMessage", "Project created successfully!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error uploading screenshot: " + e.getMessage());
        }

        return "redirect:/projects";
    }

    @PostMapping("/{id}/delete")
    public String handleDeleteProject(@PathVariable("id") Long id,
                                      @AuthenticationPrincipal UserDetails userDetails,
                                      RedirectAttributes redirectAttributes) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser != null) {
            projectService.deleteProject(id, currentUser);
            redirectAttributes.addFlashAttribute("successMessage", "Project deleted successfully.");
        }
        return "redirect:/projects";
    }
}
