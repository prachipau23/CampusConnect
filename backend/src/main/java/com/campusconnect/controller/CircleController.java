package com.campusconnect.controller;

import com.campusconnect.entity.Circle;
import com.campusconnect.entity.User;
import com.campusconnect.service.CircleService;
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
@RequestMapping("/circles")
public class CircleController {

    @Autowired
    private CircleService circleService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listCircles(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userDetails != null ? userService.findByEmail(userDetails.getUsername()).orElse(null) : null;
        List<Circle> circles = circleService.getAllCircles();
        Set<Long> joinedCircleIds = circleService.getJoinedCircleIds(currentUser);

        model.addAttribute("circles", circles);
        model.addAttribute("joinedCircleIds", joinedCircleIds);
        model.addAttribute("currentUser", currentUser);
        return "circles/list";
    }

    @PostMapping("/{id}/join")
    public String joinCircle(@PathVariable("id") Long id,
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser != null) {
            circleService.joinCircle(id, currentUser);
            redirectAttributes.addFlashAttribute("successMessage", "Joined circle successfully!");
        }
        return "redirect:/circles";
    }

    @PostMapping("/{id}/leave")
    public String leaveCircle(@PathVariable("id") Long id,
                              @AuthenticationPrincipal UserDetails userDetails,
                              RedirectAttributes redirectAttributes) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser != null) {
            circleService.leaveCircle(id, currentUser);
            redirectAttributes.addFlashAttribute("infoMessage", "Left circle.");
        }
        return "redirect:/circles";
    }
}
