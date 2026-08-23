package com.campusconnect.service;

import com.campusconnect.entity.Internship;
import com.campusconnect.entity.InternshipBookmark;
import com.campusconnect.entity.User;
import com.campusconnect.repository.InternshipBookmarkRepository;
import com.campusconnect.repository.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InternshipService {

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private InternshipBookmarkRepository bookmarkRepository;

    public List<Internship> searchInternships(String query, String type) {
        return internshipRepository.searchInternships(query, type);
    }

    public Set<Long> getBookmarkedInternshipIds(User user) {
        if (user == null) return Set.of();
        return bookmarkRepository.findByUser(user).stream()
                .map(ib -> ib.getInternship().getId())
                .collect(Collectors.toSet());
    }

    @Transactional
    public boolean toggleBookmark(Long internshipId, User user) {
        Internship internship = internshipRepository.findById(internshipId).orElse(null);
        if (internship == null) return false;

        if (bookmarkRepository.existsByInternshipAndUser(internship, user)) {
            bookmarkRepository.deleteByInternshipAndUser(internship, user);
            return false;
        } else {
            bookmarkRepository.save(new InternshipBookmark(internship, user));
            return true;
        }
    }

    @Transactional
    public Internship saveInternship(Internship i) {
        return internshipRepository.save(i);
    }

    @Transactional
    public void deleteInternship(Long id) {
        internshipRepository.deleteById(id);
    }
}
