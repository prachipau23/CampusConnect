package com.campusconnect.controller;

import com.campusconnect.entity.Notification;
import com.campusconnect.entity.User;
import com.campusconnect.service.NotificationService;
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
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewNotifications(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser == null) return "redirect:/login";

        List<Notification> notifications = notificationService.getUserNotifications(currentUser);
        long unreadCount = notificationService.getUnreadCount(currentUser);

        model.addAttribute("notifications", notifications);
        model.addAttribute("unreadCount", unreadCount);
        model.addAttribute("currentUser", currentUser);
        return "notifications/list";
    }

    @PostMapping("/{id}/toggle-read")
    public String toggleRead(@PathVariable("id") Long id,
                             @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser != null) {
            notificationService.toggleRead(id, currentUser);
        }
        return "redirect:/notifications";
    }

    @PostMapping("/mark-all-read")
    public String markAllRead(@AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser != null) {
            notificationService.markAllRead(currentUser);
            redirectAttributes.addFlashAttribute("successMessage", "All notifications marked as read.");
        }
        return "redirect:/notifications";
    }
}
