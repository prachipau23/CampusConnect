package com.campusconnect.repository;

import com.campusconnect.entity.Team;
import com.campusconnect.entity.TeamTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamTaskRepository extends JpaRepository<TeamTask, Long> {
    List<TeamTask> findByTeam(Team team);
}
