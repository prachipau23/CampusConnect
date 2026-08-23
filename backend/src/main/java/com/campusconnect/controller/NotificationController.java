package com.campusconnect.controller;

import com.campusconnect.entity.Notification;
import com.campusconnect.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> getMyNotifications(Authentication auth) {
        return ResponseEntity.ok(notificationService.getForUser(auth.getName()));
    }

    /**
     * GET /api/notifications/{id} — returns the notification details
     * AND the resolved redirect target URL based on entity type + id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        Notification n = notificationService.getById(id);
        String redirectUrl = notificationService.resolveRedirectUrl(id);
        return ResponseEntity.ok(Map.of(
                "id", n.getId(),
                "message", n.getMessage(),
                "targetEntityType", n.getTargetEntityType(),
                "targetEntityId", n.getTargetEntityId() != null ? n.getTargetEntityId() : "",
                "read", n.isRead(),
                "createdAt", n.getCreatedAt(),
                "redirectUrl", redirectUrl
        ));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Notification> markRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markRead(id));
    }

    @PostMapping("/mark-all-read")
    public ResponseEntity<Map<String, String>> markAllRead(Authentication auth) {
        notificationService.markAllRead(auth.getName());
        return ResponseEntity.ok(Map.of("message", "All notifications marked as read"));
    }
}
