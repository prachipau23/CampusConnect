package com.campusconnect.controller;

import com.campusconnect.entity.Hackathon;
import com.campusconnect.entity.Internship;
import com.campusconnect.entity.User;
import com.campusconnect.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private HackathonService hackathonService;

    @Autowired
    private InternshipService internshipService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);

        Map<String, Object> stats = adminService.getPlatformStats();
        List<User> students = adminService.getAllUsers();
        var projects = projectService.getAllProjects(null, null);
        var hackathons = hackathonService.searchHackathons(null, null);
        var internships = internshipService.searchInternships(null, null);

        model.addAttribute("stats", stats);
        model.addAttribute("students", students);
        model.addAttribute("projects", projects);
        model.addAttribute("hackathons", hackathons);
        model.addAttribute("internships", internships);
        model.addAttribute("announcements", adminService.getSystemAnnouncements());
        model.addAttribute("currentUser", currentUser);

        return "admin/dashboard";
    }

    @PostMapping("/users/{id}/toggle-active")
    public String toggleUserActive(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        adminService.toggleUserActiveStatus(id);
        redirectAttributes.addFlashAttribute("successMessage", "Student account status updated.");
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/projects/{id}/verify")
    public String toggleProjectVerification(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        projectService.toggleVerification(id);
        redirectAttributes.addFlashAttribute("successMessage", "Project verification status updated.");
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/hackathons/add")
    public String addHackathon(@ModelAttribute Hackathon hackathon, RedirectAttributes redirectAttributes) {
        hackathonService.saveHackathon(hackathon);
        redirectAttributes.addFlashAttribute("successMessage", "New Hackathon event posted successfully!");
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/internships/add")
    public String addInternship(@ModelAttribute Internship internship, RedirectAttributes redirectAttributes) {
        internshipService.saveInternship(internship);
        redirectAttributes.addFlashAttribute("successMessage", "New Internship opportunity posted successfully!");
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/announcements/publish")
    public String publishAnnouncement(@RequestParam("title") String title,
                                      @RequestParam("content") String content,
                                      @RequestParam("priority") String priority,
                                      RedirectAttributes redirectAttributes) {
        adminService.publishAnnouncement(title, content, priority);
        redirectAttributes.addFlashAttribute("successMessage", "Announcement published and broadcasted to all students!");
        return "redirect:/admin/dashboard";
    }
}
