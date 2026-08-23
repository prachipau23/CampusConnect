package com.campusconnect.repository;

import com.campusconnect.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query("SELECT r FROM Resource r WHERE " +
           "(:query IS NULL OR :query = '' OR LOWER(r.title) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:category IS NULL OR :category = '' OR r.category = :category) " +
           "ORDER BY r.createdAt DESC")
    List<Resource> searchResources(@Param("query") String query, @Param("category") String category);
}
