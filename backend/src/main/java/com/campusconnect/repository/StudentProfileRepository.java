package com.campusconnect.repository;

import com.campusconnect.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    Optional<StudentProfile> findByUserId(Long userId);

    @Query("SELECT p FROM StudentProfile p WHERE " +
           "LOWER(p.fullName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.department) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.skills) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<StudentProfile> search(@Param("q") String query);
}
