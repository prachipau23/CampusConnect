package com.campusconnect.service;

import com.campusconnect.entity.Notification;
import com.campusconnect.entity.User;
import com.campusconnect.repository.NotificationRepository;
import com.campusconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Value("${app.cors.allowed-origin:http://localhost:3000}")
    private String frontendOrigin;

    public List<Notification> getForUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return notificationRepository.findByTargetUserIdOrderByCreatedAtDesc(user.getId());
    }

    public Notification getById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found: " + id));
    }

    /**
     * Resolves the redirect target URL for a notification based on its
     * targetEntityType and targetEntityId. This is a real computation,
     * not a static list.
     */
    public String resolveRedirectUrl(Long notificationId) {
        Notification n = getById(notificationId);
        Long entityId = n.getTargetEntityId();
        String base = frontendOrigin;

        return switch (n.getTargetEntityType()) {
            case PROJECT -> base + "/projects.html#project-" + entityId;
            case TEAM -> base + "/teams.html#team-" + entityId;
            case HACKATHON -> base + "/hackathons.html#hackathon-" + entityId;
            case INTERNSHIP -> base + "/internships.html#internship-" + entityId;
            case CIRCLE -> base + "/circles.html#circle-" + entityId;
            case RESOURCE -> base + "/resources.html#resource-" + entityId;
            case WORKSPACE -> base + "/team-workspace.html?teamId=" + entityId;
            case PROFILE -> base + "/profile.html?userId=" + entityId;
            case SYSTEM -> base + "/notifications.html";
        };
    }

    @Transactional
    public Notification markRead(Long id) {
        Notification n = getById(id);
        n.setRead(true);
        return notificationRepository.save(n);
    }

    @Transactional
    public void markAllRead(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Notification> unread = notificationRepository.findByTargetUserIdAndReadFalse(user.getId());
        unread.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unread);
    }

    public Notification createNotification(User targetUser, String message,
                                           Notification.EntityType entityType, Long entityId) {
        Notification n = Notification.builder()
                .targetUser(targetUser)
                .title(entityType != null ? entityType.name() + " Update" : "CampusConnect Notification")
                .message(message)
                .targetEntityType(entityType)
                .targetEntityId(entityId)
                .read(false)
                .build();
        return notificationRepository.save(n);
    }
}
