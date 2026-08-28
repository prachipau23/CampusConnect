package com.campusconnect.controller;

import com.campusconnect.entity.StudentProfile;
import com.campusconnect.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'MENTOR')")
public class AdminController {

    private final AdminService adminService;

    /**
     * GET /api/admin/dashboard — returns all student profiles with GPA and performance data.
     * Role-gated: ADMIN or TEACHER only. Returns 403 for STUDENT role.
     */
    @GetMapping("/dashboard")
    public ResponseEntity<List<StudentProfile>> getDashboard() {
        return ResponseEntity.ok(adminService.getAllStudentProfiles());
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentProfile>> getAllStudents() {
        return ResponseEntity.ok(adminService.getAllStudentProfiles());
    }
}
