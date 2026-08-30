package com.campusconnect.controller;

import com.campusconnect.entity.StudentProfile;
import com.campusconnect.service.AdminService;
import com.campusconnect.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    private final ProfileService profileService;

    /**
     * GET /api/admin/dashboard — returns all student profiles with GPA and performance data.
     * Role-gated: ADMIN, TEACHER, MENTOR only. Returns 403 for STUDENT role.
     */
    @GetMapping("/dashboard")
    public ResponseEntity<List<StudentProfile>> getDashboard(
            @RequestParam(required = false) Double minGpa,
            @RequestParam(required = false) Double maxGpa) {
        return ResponseEntity.ok(adminService.getStudentProfilesWithGpaFilter(minGpa, maxGpa));
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentProfile>> getAllStudents(
            @RequestParam(required = false) Double minGpa,
            @RequestParam(required = false) Double maxGpa) {
        return ResponseEntity.ok(adminService.getStudentProfilesWithGpaFilter(minGpa, maxGpa));
    }

    /**
     * GET /api/admin/students/{id}/resume/download — download resume of any student.
     * Role-gated: ADMIN, TEACHER, MENTOR only. Returns 403 for STUDENT role.
     */
    @GetMapping("/students/{id}/resume/download")
    public ResponseEntity<byte[]> downloadStudentResume(@PathVariable Long id) {
        StudentProfile profile = profileService.getResumeProfileByProfileOrUserId(id);

        String filename = profile.getResumeFileName() != null ? profile.getResumeFileName() : "resume.pdf";
        String contentType = profile.getResumeFileType() != null ? profile.getResumeFileType() : MediaType.APPLICATION_OCTET_STREAM_VALUE;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(profile.getResumeData());
    }
}
