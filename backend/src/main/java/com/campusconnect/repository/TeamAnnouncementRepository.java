package com.campusconnect.repository;

import com.campusconnect.entity.Team;
import com.campusconnect.entity.TeamAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamAnnouncementRepository extends JpaRepository<TeamAnnouncement, Long> {
    List<TeamAnnouncement> findByTeamOrderByCreatedAtDesc(Team team);
}
