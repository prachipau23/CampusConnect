package com.campusconnect.service;

import com.campusconnect.entity.StudentProfile;
import com.campusconnect.repository.StudentProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final StudentProfileRepository profileRepository;

    public List<StudentProfile> getAllStudentProfiles() {
        return profileRepository.findAll();
    }
}
