package com.campusconnect.service;

import com.campusconnect.entity.StudentProfile;
import com.campusconnect.repository.StudentProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectoryService {

    @Autowired
    private StudentProfileRepository profileRepository;

    public List<StudentProfile> searchStudents(String query, String department, String year) {
        return profileRepository.searchProfiles(query, department, year);
    }
}
