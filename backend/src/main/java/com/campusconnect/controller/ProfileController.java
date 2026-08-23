package com.campusconnect.controller;

import com.campusconnect.entity.StudentProfile;
import com.campusconnect.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profiles")
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

    @GetMapping("/{userId}")
    public ResponseEntity<StudentProfile> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getByUserId(userId));
    }
}
