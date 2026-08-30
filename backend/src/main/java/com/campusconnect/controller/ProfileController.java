package com.campusconnect.controller;

import com.campusconnect.entity.StudentProfile;
import com.campusconnect.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/profiles", "/api/profile"})
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<StudentProfile> getMyProfile(Authentication auth) {
        var user = profileService.getUserByEmail(auth.getName());
        return ResponseEntity.ok(profileService.getByUserId(user.getId()));
    }

    @PutMapping("/me")
    public ResponseEntity<StudentProfile> updateMyProfile(Authentication auth,
                                                          @RequestBody Map<String, Object> updates) {
        var user = profileService.getUserByEmail(auth.getName());
        return ResponseEntity.ok(profileService.updateProfile(user.getId(), updates));
    }

    @PostMapping(value = "/resume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudentProfile> uploadResume(Authentication auth,
                                                       @RequestParam("file") MultipartFile file) throws IOException {
        var user = profileService.getUserByEmail(auth.getName());
        return ResponseEntity.ok(profileService.uploadResume(user.getId(), file));
    }

    @GetMapping("/resume/download")
    public ResponseEntity<byte[]> downloadMyResume(Authentication auth) {
        var user = profileService.getUserByEmail(auth.getName());
        StudentProfile profile = profileService.getResumeProfile(user.getId());

        String filename = profile.getResumeFileName() != null ? profile.getResumeFileName() : "resume.pdf";
        String contentType = profile.getResumeFileType() != null ? profile.getResumeFileType() : MediaType.APPLICATION_OCTET_STREAM_VALUE;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(profile.getResumeData());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<StudentProfile> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getByUserId(userId));
    }
}
