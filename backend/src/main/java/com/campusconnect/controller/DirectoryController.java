package com.campusconnect.controller;

import com.campusconnect.entity.StudentProfile;
import com.campusconnect.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directory")
@RequiredArgsConstructor
public class DirectoryController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<List<StudentProfile>> search(
            @RequestParam(required = false) String q) {
        return ResponseEntity.ok(profileService.search(q));
    }
}
