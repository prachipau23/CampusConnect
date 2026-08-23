package com.campusconnect.controller;

import com.campusconnect.entity.Hackathon;
import com.campusconnect.entity.User;
import com.campusconnect.service.HackathonService;
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
@RequestMapping("/hackathons")
public class HackathonController {

    @Autowired
    private HackathonService hackathonService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listHackathons(@RequestParam(value = "query", required = false) String query,
                                 @RequestParam(value = "status", required = false) String status,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 Model model) {

        User currentUser = userDetails != null ? userService.findByEmail(userDetails.getUsername()).orElse(null) : null;
        List<Hackathon> hackathons = hackathonService.searchHackathons(query, status);
        Set<Long> interestedIds = hackathonService.getInterestedHackathonIds(currentUser);

        model.addAttribute("hackathons", hackathons);
        model.addAttribute("interestedIds", interestedIds);
        model.addAttribute("query", query);
        model.addAttribute("status", status);
        model.addAttribute("currentUser", currentUser);
        return "hackathons/list";
    }

    @PostMapping("/{id}/register")
    public String registerInterest(@PathVariable("id") Long id,
                                   @RequestParam("track") String track,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   RedirectAttributes redirectAttributes) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser == null) return "redirect:/login";

        boolean registered = hackathonService.registerInterest(id, currentUser, track);
        if (registered) {
            redirectAttributes.addFlashAttribute("successMessage", "Team / Individual registration interest recorded!");
        } else {
            redirectAttributes.addFlashAttribute("infoMessage", "You have already registered interest for this hackathon.");
        }
        return "redirect:/hackathons";
    }
}
