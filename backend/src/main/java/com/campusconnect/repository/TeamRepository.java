package com.campusconnect.repository;

import com.campusconnect.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByStatus(Team.TeamStatus status);
    List<Team> findAllByOrderByCreatedAtDesc();
}
