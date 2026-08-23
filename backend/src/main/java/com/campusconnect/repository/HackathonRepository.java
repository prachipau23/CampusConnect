package com.campusconnect.repository;

import com.campusconnect.entity.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
    List<Hackathon> findAllByOrderByStartDateAsc();
    List<Hackathon> findAllByOrderByCreatedAtDesc();
}
