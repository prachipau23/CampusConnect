package com.campusconnect.repository;

import com.campusconnect.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

    @Query("SELECT p FROM StudentProfile p WHERE " +
           "(:minGpa IS NULL OR (p.gpa IS NOT NULL AND p.gpa >= :minGpa)) AND " +
           "(:maxGpa IS NULL OR (p.gpa IS NOT NULL AND p.gpa <= :maxGpa)) " +
           "ORDER BY p.gpa DESC NULLS LAST, p.fullName ASC")
    List<StudentProfile> findByGpaRange(@Param("minGpa") BigDecimal minGpa, @Param("maxGpa") BigDecimal maxGpa);
}
