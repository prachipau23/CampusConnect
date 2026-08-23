package com.campusconnect.repository;

import com.campusconnect.entity.Hackathon;
import com.campusconnect.entity.HackathonInterest;
import com.campusconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HackathonInterestRepository extends JpaRepository<HackathonInterest, Long> {
    Optional<HackathonInterest> findByHackathonAndUser(Hackathon hackathon, User user);
    boolean existsByHackathonAndUser(Hackathon hackathon, User user);
    List<HackathonInterest> findByUser(User user);
}
