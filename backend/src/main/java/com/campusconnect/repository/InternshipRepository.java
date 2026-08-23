package com.campusconnect.repository;

import com.campusconnect.entity.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {
    List<Internship> findAllByOrderByDeadlineAsc();
    List<Internship> findByCompanyContainingIgnoreCase(String company);
}
