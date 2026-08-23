package com.campusconnect.controller;

import com.campusconnect.entity.Circle;
import com.campusconnect.service.CircleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/circles")
@RequiredArgsConstructor
public class CircleController {

    private final CircleService circleService;

    @GetMapping
    public ResponseEntity<List<Circle>> list() {
        return ResponseEntity.ok(circleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Circle> getById(@PathVariable Long id) {
        return ResponseEntity.ok(circleService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Circle> create(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(circleService.create(body));
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<Map<String, String>> join(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(circleService.join(id, auth.getName()));
    }

    @DeleteMapping("/{id}/leave")
    public ResponseEntity<Map<String, String>> leave(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(circleService.leave(id, auth.getName()));
    }
}
