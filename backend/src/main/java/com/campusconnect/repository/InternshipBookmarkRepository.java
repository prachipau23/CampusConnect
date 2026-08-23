package com.campusconnect.repository;

import com.campusconnect.entity.Internship;
import com.campusconnect.entity.InternshipBookmark;
import com.campusconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternshipBookmarkRepository extends JpaRepository<InternshipBookmark, Long> {
    Optional<InternshipBookmark> findByInternshipAndUser(Internship internship, User user);
    boolean existsByInternshipAndUser(Internship internship, User user);
    List<InternshipBookmark> findByUser(User user);
    void deleteByInternshipAndUser(Internship internship, User user);
}
