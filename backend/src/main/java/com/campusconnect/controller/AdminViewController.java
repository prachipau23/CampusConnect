package com.campusconnect.controller;

import com.campusconnect.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/views/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
public class AdminViewController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("profiles", adminService.getAllStudentProfiles());
        return "admin-dashboard";
    }
}
