package com.campusconnect.repository;

import com.campusconnect.entity.Circle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CircleRepository extends JpaRepository<Circle, Long> {
    Optional<Circle> findByName(String name);
}
