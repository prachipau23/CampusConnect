package com.campusconnect.controller;

import com.campusconnect.entity.Internship;
import com.campusconnect.entity.User;
import com.campusconnect.service.InternshipService;
import com.campusconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/internships")
public class InternshipController {

    @Autowired
    private InternshipService internshipService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listInternships(@RequestParam(value = "query", required = false) String query,
                                  @RequestParam(value = "type", required = false) String type,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {

        User currentUser = userDetails != null ? userService.findByEmail(userDetails.getUsername()).orElse(null) : null;
        List<Internship> internships = internshipService.searchInternships(query, type);
        Set<Long> bookmarkedIds = internshipService.getBookmarkedInternshipIds(currentUser);

        model.addAttribute("internships", internships);
        model.addAttribute("bookmarkedIds", bookmarkedIds);
        model.addAttribute("query", query);
        model.addAttribute("type", type);
        model.addAttribute("currentUser", currentUser);
        return "internships/list";
    }

    @PostMapping("/{id}/bookmark")
    public String toggleBookmark(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 RedirectAttributes redirectAttributes) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser == null) return "redirect:/login";

        boolean bookmarked = internshipService.toggleBookmark(id, currentUser);
        if (bookmarked) {
            redirectAttributes.addFlashAttribute("successMessage", "Internship opportunity saved to your bookmarks!");
        } else {
            redirectAttributes.addFlashAttribute("infoMessage", "Removed from bookmarks.");
        }
        return "redirect:/internships";
    }
}
