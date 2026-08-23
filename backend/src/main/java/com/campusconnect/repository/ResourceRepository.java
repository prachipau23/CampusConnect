package com.campusconnect.repository;

import com.campusconnect.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    // List without loading file bytes (project only metadata columns)
    @Query("SELECT r FROM Resource r ORDER BY r.createdAt DESC")
    List<Resource> findAllOrderByCreatedAtDesc();

    List<Resource> findByCategory(String category);
    List<Resource> findByUploadedById(Long userId);
}
