package com.campusconnect.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "circle_memberships",
       uniqueConstraints = @UniqueConstraint(columnNames = {"circle_id", "user_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CircleMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "circle_id", nullable = false)
    @JsonIgnoreProperties({"memberships", "hibernateLazyInitializer", "handler"})
    private Circle circle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"profile", "passwordHash", "hibernateLazyInitializer", "handler"})
    private User user;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime joinedAt;
}
