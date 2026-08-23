package com.campusconnect.service;

import com.campusconnect.entity.StudentProfile;
import com.campusconnect.entity.User;
import com.campusconnect.repository.StudentProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ProfileService {

    @Autowired
    private StudentProfileRepository profileRepository;

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    public StudentProfile getProfileByUser(User user) {
        return profileRepository.findByUser(user)
                .orElseGet(() -> {
                    StudentProfile p = new StudentProfile();
                    p.setUser(user);
                    return profileRepository.save(p);
                });
    }

    public StudentProfile getProfileById(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    @Transactional
    public StudentProfile updateProfile(User user, String fullName, String college, String department,
                                         String academicYear, String skills, String aboutMe,
                                         String githubUrl, String linkedinUrl,
                                         MultipartFile resumeFile, MultipartFile picFile) throws IOException {

        StudentProfile profile = getProfileByUser(user);
        profile.setFullName(fullName);
        profile.setCollege(college);
        profile.setDepartment(department);
        profile.setAcademicYear(academicYear);
        profile.setSkills(skills);
        profile.setAboutMe(aboutMe);
        profile.setGithubUrl(githubUrl);
        profile.setLinkedinUrl(linkedinUrl);

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        if (resumeFile != null && !resumeFile.isEmpty()) {
            String resumeFilename = "resume_" + user.getId() + "_" + UUID.randomUUID().toString().substring(0, 8) + "_" + resumeFile.getOriginalFilename();
            Path targetPath = uploadPath.resolve(resumeFilename);
            Files.copy(resumeFile.getInputStream(), targetPath);
            profile.setResumePath("/uploads/" + resumeFilename);
        }

        if (picFile != null && !picFile.isEmpty()) {
            String picFilename = "pic_" + user.getId() + "_" + UUID.randomUUID().toString().substring(0, 8) + "_" + picFile.getOriginalFilename();
            Path targetPath = uploadPath.resolve(picFilename);
            Files.copy(picFile.getInputStream(), targetPath);
            profile.setProfilePicPath("/uploads/" + picFilename);
        }

        profile.calculateCompletion();
        return profileRepository.save(profile);
    }
}
