package com.campusconnect.controller;

import com.campusconnect.entity.Team;
import com.campusconnect.entity.WorkspacePost;
import com.campusconnect.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<Team>> list() {
        return ResponseEntity.ok(teamService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Team> create(@RequestBody Map<String, Object> body, Authentication auth) {
        return ResponseEntity.ok(teamService.create(body, auth.getName()));
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<Map<String, String>> join(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(teamService.join(id, auth.getName()));
    }

    @DeleteMapping("/{id}/leave")
    public ResponseEntity<Map<String, String>> leave(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(teamService.leave(id, auth.getName()));
    }

    // Workspace endpoints nested under team
    @GetMapping("/{id}/workspace")
    public ResponseEntity<List<WorkspacePost>> getWorkspace(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getWorkspacePosts(id));
    }

    @PostMapping("/{id}/workspace")
    public ResponseEntity<WorkspacePost> addWorkspacePost(@PathVariable Long id,
                                                           @RequestBody Map<String, String> body,
                                                           Authentication auth) {
        return ResponseEntity.ok(teamService.addWorkspacePost(
                id, body.get("content"), body.get("postType"), auth.getName()));
    }
}
