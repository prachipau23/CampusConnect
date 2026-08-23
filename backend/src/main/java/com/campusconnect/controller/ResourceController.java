package com.campusconnect.controller;

import com.campusconnect.entity.Resource;
import com.campusconnect.entity.User;
import com.campusconnect.service.ResourceService;
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
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listResources(@RequestParam(value = "query", required = false) String query,
                                @RequestParam(value = "category", required = false) String category,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {

        User currentUser = userDetails != null ? userService.findByEmail(userDetails.getUsername()).orElse(null) : null;
        List<Resource> resources = resourceService.searchResources(query, category);

        model.addAttribute("resources", resources);
        model.addAttribute("query", query);
        model.addAttribute("category", category);
        model.addAttribute("currentUser", currentUser);
        return "resources/list";
    }

    @PostMapping("/upload")
    public String handleUpload(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestParam("title") String title,
                               @RequestParam("category") String category,
                               @RequestParam("file") MultipartFile file,
                               RedirectAttributes redirectAttributes) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser == null) return "redirect:/login";

        try {
            resourceService.uploadResource(currentUser, title, category, file);
            redirectAttributes.addFlashAttribute("successMessage", "Shared resource uploaded successfully to Campus Library!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error uploading file: " + e.getMessage());
        }

        return "redirect:/resources";
    }

    @PostMapping("/{id}/download")
    public String handleDownload(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Resource res = resourceService.incrementDownload(id);
        if (res != null) {
            return "redirect:" + (res.getFilePath() != null ? res.getFilePath() : "/resources");
        }
        return "redirect:/resources";
    }
}
