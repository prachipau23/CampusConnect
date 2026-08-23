package com.campusconnect.repository;

import com.campusconnect.entity.StudentProfile;
import com.campusconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    Optional<StudentProfile> findByUser(User user);
    Optional<StudentProfile> findByUserId(Long userId);

    @Query("SELECT p FROM StudentProfile p WHERE " +
           "(:query IS NULL OR :query = '' OR LOWER(p.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.skills) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:department IS NULL OR :department = '' OR p.department = :department) AND " +
           "(:year IS NULL OR :year = '' OR p.academicYear = :year)")
    List<StudentProfile> searchProfiles(@Param("query") String query,
                                        @Param("department") String department,
                                        @Param("year") String year);
}
