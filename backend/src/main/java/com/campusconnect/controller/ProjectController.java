package com.campusconnect.controller;

import com.campusconnect.entity.Project;
import com.campusconnect.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> list(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(projectService.search(q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody Map<String, Object> body, Authentication auth) {
        return ResponseEntity.ok(projectService.create(body, auth.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable Long id,
                                          @RequestBody Map<String, Object> body,
                                          Authentication auth) {
        return ResponseEntity.ok(projectService.update(id, body, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id, Authentication auth) {
        projectService.delete(id, auth.getName());
        return ResponseEntity.ok(Map.of("message", "Project deleted"));
    }
}
