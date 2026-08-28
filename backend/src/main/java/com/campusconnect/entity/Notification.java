package com.campusconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "target_user_id", nullable = false)
    private User targetUser;

    @Column(nullable = false)
    @Builder.Default
    private String title = "CampusConnect Notification";

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_entity_type", nullable = false)
    private EntityType targetEntityType;

    @Column(name = "target_entity_id")
    private Long targetEntityId;

    @Column(name = "read", nullable = false)
    @Builder.Default
    private boolean read = false;

    @Column(name = "is_read", nullable = false)
    @Builder.Default
    private boolean isRead = false;

    public void setRead(boolean read) {
        this.read = read;
        this.isRead = read;
    }

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public enum EntityType {
        PROJECT, TEAM, CIRCLE, HACKATHON, INTERNSHIP, RESOURCE, WORKSPACE, PROFILE, SYSTEM
    }
}
