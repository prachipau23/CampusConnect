package com.campusconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "hackathons")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String organizer;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(precision = 12, scale = 2)
    private BigDecimal prizeAmount;

    private String registrationUrl;
    private String mode;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
