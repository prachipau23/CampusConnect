package com.campusconnect.service;

import com.campusconnect.entity.StudentProfile;
import com.campusconnect.repository.StudentProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final StudentProfileRepository profileRepository;

    public List<StudentProfile> getAllStudentProfiles() {
        return profileRepository.findAll();
    }

    public List<StudentProfile> getStudentProfilesWithGpaFilter(Double minGpa, Double maxGpa) {
        if (minGpa == null && maxGpa == null) {
            return profileRepository.findAll();
        }
        BigDecimal min = minGpa != null ? BigDecimal.valueOf(minGpa) : null;
        BigDecimal max = maxGpa != null ? BigDecimal.valueOf(maxGpa) : null;
        return profileRepository.findByGpaRange(min, max);
    }
}
