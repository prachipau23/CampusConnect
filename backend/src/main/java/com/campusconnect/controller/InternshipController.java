package com.campusconnect.controller;

import com.campusconnect.entity.Internship;
import com.campusconnect.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @GetMapping
    public ResponseEntity<List<Internship>> list() {
        return ResponseEntity.ok(internshipService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Internship> getById(@PathVariable Long id) {
        return ResponseEntity.ok(internshipService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Internship> create(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(internshipService.create(body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        internshipService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Internship deleted"));
    }
}
