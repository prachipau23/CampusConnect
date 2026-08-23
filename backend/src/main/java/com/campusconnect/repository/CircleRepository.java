package com.campusconnect.repository;

import com.campusconnect.entity.Circle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CircleRepository extends JpaRepository<Circle, Long> {
    List<Circle> findByCategory(String category);
    List<Circle> findAllByOrderByMemberCountDesc();
}
