package com.campusconnect.controller;

import com.campusconnect.entity.StudentProfile;
import com.campusconnect.entity.User;
import com.campusconnect.service.ProfileService;
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

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewMyProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser == null) return "redirect:/login";

        StudentProfile profile = profileService.getProfileByUser(currentUser);
        model.addAttribute("profile", profile);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isOwnProfile", true);
        return "profile/view";
    }

    @GetMapping("/{id}")
    public String viewProfileById(@PathVariable("id") Long id,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        StudentProfile profile = profileService.getProfileById(id);
        if (profile == null) return "redirect:/directory";

        boolean isOwnProfile = currentUser != null && currentUser.getId().equals(profile.getUser().getId());
        model.addAttribute("profile", profile);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isOwnProfile", isOwnProfile);
        return "profile/view";
    }

    @GetMapping("/edit")
    public String editProfilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser == null) return "redirect:/login";

        StudentProfile profile = profileService.getProfileByUser(currentUser);
        model.addAttribute("profile", profile);
        return "profile/edit";
    }

    @PostMapping("/edit")
    public String handleEditProfile(@AuthenticationPrincipal UserDetails userDetails,
                                    @RequestParam("fullName") String fullName,
                                    @RequestParam("college") String college,
                                    @RequestParam("department") String department,
                                    @RequestParam("academicYear") String academicYear,
                                    @RequestParam("skills") String skills,
                                    @RequestParam("aboutMe") String aboutMe,
                                    @RequestParam("githubUrl") String githubUrl,
                                    @RequestParam("linkedinUrl") String linkedinUrl,
                                    @RequestParam(value = "resumeFile", required = false) MultipartFile resumeFile,
                                    @RequestParam(value = "picFile", required = false) MultipartFile picFile,
                                    RedirectAttributes redirectAttributes) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser == null) return "redirect:/login";

        try {
            profileService.updateProfile(currentUser, fullName, college, department, academicYear,
                    skills, aboutMe, githubUrl, linkedinUrl, resumeFile, picFile);
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error uploading files: " + e.getMessage());
        }

        return "redirect:/profile";
    }
}
