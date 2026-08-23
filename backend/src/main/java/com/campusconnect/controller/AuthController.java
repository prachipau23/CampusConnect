package com.campusconnect.controller;

import com.campusconnect.entity.User;
import com.campusconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (error != null) model.addAttribute("errorMessage", "Invalid email or password.");
        if (logout != null) model.addAttribute("successMessage", "You have been logged out successfully.");
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("fullName") String fullName,
                                 @RequestParam("department") String department,
                                 @RequestParam("academicYear") String academicYear,
                                 @RequestParam("securityQuestion") String securityQuestion,
                                 @RequestParam("securityAnswer") String securityAnswer,
                                 RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(email, password, fullName, department, academicYear, securityQuestion, securityAnswer);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email,
                                       @RequestParam("securityAnswer") String securityAnswer,
                                       @RequestParam("newPassword") String newPassword,
                                       RedirectAttributes redirectAttributes) {
        boolean success = userService.resetPassword(email, securityAnswer, newPassword);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Password reset successfully! Please login with your new password.");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Email or security answer was incorrect.");
            return "redirect:/forgot-password";
        }
    }
}
