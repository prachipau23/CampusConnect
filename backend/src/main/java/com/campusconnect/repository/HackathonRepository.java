package com.campusconnect.repository;

import com.campusconnect.entity.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {

    @Query("SELECT h FROM Hackathon h WHERE " +
           "(:query IS NULL OR :query = '' OR LOWER(h.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(h.tracks) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:status IS NULL OR :status = '' OR h.status = :status)")
    List<Hackathon> searchHackathons(@Param("query") String query, @Param("status") String status);
}
