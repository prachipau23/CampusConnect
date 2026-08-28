package com.campusconnect.controller;

import com.campusconnect.entity.Hackathon;
import com.campusconnect.service.HackathonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hackathons")
@RequiredArgsConstructor
public class HackathonController {

    private final HackathonService hackathonService;

    @GetMapping
    public ResponseEntity<List<Hackathon>> list() {
        return ResponseEntity.ok(hackathonService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hackathon> getById(@PathVariable Long id) {
        return ResponseEntity.ok(hackathonService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Hackathon> create(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(hackathonService.create(body));
    }

    @PostMapping("/{id}/register")
    public ResponseEntity<com.campusconnect.entity.Team> registerTeam(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body,
            org.springframework.security.core.Authentication auth) {
        return ResponseEntity.ok(hackathonService.registerTeam(id, body, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        hackathonService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Hackathon deleted"));
    }
}
