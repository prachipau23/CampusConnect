package com.campusconnect.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "student_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String fullName;

    private String department;
    private String yearOfStudy;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(columnDefinition = "TEXT")
    private String skills;

    private String githubUrl;
    private String linkedinUrl;
    private String avatarInitials;
    private String resumeFileName;

    @Column(precision = 4, scale = 2)
    private BigDecimal gpa;

    @Column(columnDefinition = "TEXT")
    private String performanceNotes;
}
