package com.campusconnect.repository;

import com.campusconnect.entity.Team;
import com.campusconnect.entity.TeamDiscussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamDiscussionRepository extends JpaRepository<TeamDiscussion, Long> {
    List<TeamDiscussion> findByTeamOrderByCreatedAtDesc(Team team);
}
