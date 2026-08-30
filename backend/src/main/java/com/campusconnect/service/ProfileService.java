package com.campusconnect.service;

import com.campusconnect.entity.StudentProfile;
import com.campusconnect.entity.User;
import com.campusconnect.repository.StudentProfileRepository;
import com.campusconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private static final long MAX_RESUME_SIZE = 5 * 1024 * 1024L; // 5MB

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

    @Transactional
    public StudentProfile uploadResume(Long userId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        if (file.getSize() > MAX_RESUME_SIZE) {
            throw new IllegalArgumentException("File size exceeds maximum limit of 5MB");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            originalFilename = "resume.pdf";
        }

        String lowerFilename = originalFilename.toLowerCase();
        if (!lowerFilename.endsWith(".pdf") && !lowerFilename.endsWith(".doc") && !lowerFilename.endsWith(".docx")) {
            throw new IllegalArgumentException("Only PDF, DOC, and DOCX files are allowed");
        }

        String contentType = file.getContentType();
        if (contentType == null || contentType.isBlank() || contentType.equals("application/octet-stream")) {
            if (lowerFilename.endsWith(".pdf")) contentType = "application/pdf";
            else if (lowerFilename.endsWith(".docx")) contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            else if (lowerFilename.endsWith(".doc")) contentType = "application/msword";
            else contentType = "application/octet-stream";
        }

        StudentProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user: " + userId));

        profile.setResumeFileName(originalFilename);
        profile.setResumeFileType(contentType);
        profile.setResumeData(file.getBytes());

        return profileRepository.save(profile);
    }

    public StudentProfile getResumeProfile(Long userId) {
        StudentProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user: " + userId));

        if (profile.getResumeData() == null || profile.getResumeData().length == 0) {
            throw new IllegalArgumentException("No resume uploaded for this profile");
        }
        return profile;
    }

    public StudentProfile getResumeProfileByProfileOrUserId(Long id) {
        StudentProfile profile = profileRepository.findById(id)
                .or(() -> profileRepository.findByUserId(id))
                .orElseThrow(() -> new IllegalArgumentException("Student profile not found for id: " + id));

        if (profile.getResumeData() == null || profile.getResumeData().length == 0) {
            throw new IllegalArgumentException("No resume uploaded for this student");
        }
        return profile;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .or(() -> userRepository.findByUsername(email))
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
    }
}
