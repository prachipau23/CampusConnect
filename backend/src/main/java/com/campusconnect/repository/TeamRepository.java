package com.campusconnect.repository;

import com.campusconnect.entity.Team;
import com.campusconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByCreatedBy(User createdBy);

    @Query("SELECT t FROM Team t WHERE " +
           "(:query IS NULL OR :query = '' OR LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(t.projectTitle) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(t.requiredSkills) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:status IS NULL OR :status = '' OR t.status = :status)")
    List<Team> searchTeams(@Param("query") String query, @Param("status") String status);
}
