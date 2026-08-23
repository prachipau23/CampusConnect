package com.campusconnect.service;

import com.campusconnect.entity.StudentProfile;
import com.campusconnect.entity.User;
import com.campusconnect.repository.StudentProfileRepository;
import com.campusconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final StudentProfileRepository profileRepository;
    private final UserRepository userRepository;

    public StudentProfile getByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user: " + userId));
    }

    public StudentProfile getById(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found: " + profileId));
    }

    public List<StudentProfile> getAll() {
        return profileRepository.findAll();
    }

    public List<StudentProfile> search(String query) {
        if (query == null || query.isBlank()) return profileRepository.findAll();
        return profileRepository.search(query);
    }

    @Transactional
    public StudentProfile updateProfile(Long userId, Map<String, Object> updates) {
        StudentProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        if (updates.containsKey("fullName")) profile.setFullName((String) updates.get("fullName"));
        if (updates.containsKey("department")) profile.setDepartment((String) updates.get("department"));
        if (updates.containsKey("yearOfStudy")) profile.setYearOfStudy((String) updates.get("yearOfStudy"));
        if (updates.containsKey("bio")) profile.setBio((String) updates.get("bio"));
        if (updates.containsKey("skills")) profile.setSkills((String) updates.get("skills"));
        if (updates.containsKey("githubUrl")) profile.setGithubUrl((String) updates.get("githubUrl"));
        if (updates.containsKey("linkedinUrl")) profile.setLinkedinUrl((String) updates.get("linkedinUrl"));
        if (updates.containsKey("performanceNotes")) profile.setPerformanceNotes((String) updates.get("performanceNotes"));

        return profileRepository.save(profile);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
    }
}
