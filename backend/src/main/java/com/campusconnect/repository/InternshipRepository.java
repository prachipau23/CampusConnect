package com.campusconnect.repository;

import com.campusconnect.entity.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {

    @Query("SELECT i FROM Internship i WHERE " +
           "(:query IS NULL OR :query = '' OR LOWER(i.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(i.company) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:type IS NULL OR :type = '' OR LOWER(i.type) LIKE LOWER(CONCAT('%', :type, '%')))")
    List<Internship> searchInternships(@Param("query") String query, @Param("type") String type);
}
