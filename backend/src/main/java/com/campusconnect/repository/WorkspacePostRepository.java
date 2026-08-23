package com.campusconnect.repository;

import com.campusconnect.entity.WorkspacePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkspacePostRepository extends JpaRepository<WorkspacePost, Long> {
    List<WorkspacePost> findByTeamIdOrderByCreatedAtDesc(Long teamId);
}
