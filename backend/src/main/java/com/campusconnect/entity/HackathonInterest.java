package com.campusconnect.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hackathon_interests")
public class HackathonInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hackathon_id", nullable = false)
    private Hackathon hackathon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String track;
    private LocalDateTime registeredAt = LocalDateTime.now();

    public HackathonInterest() {}

    public HackathonInterest(Hackathon hackathon, User user, String track) {
        this.hackathon = hackathon;
        this.user = user;
        this.track = track;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Hackathon getHackathon() { return hackathon; }
    public void setHackathon(Hackathon hackathon) { this.hackathon = hackathon; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getTrack() { return track; }
    public void setTrack(String track) { this.track = track; }

    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
}
