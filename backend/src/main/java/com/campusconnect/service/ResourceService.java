package com.campusconnect.service;

import com.campusconnect.entity.Resource;
import com.campusconnect.entity.User;
import com.campusconnect.repository.ResourceRepository;
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
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    public List<Resource> getAll() {
        return resourceRepository.findAllOrderByCreatedAtDesc();
    }

    public Resource getById(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found: " + id));
    }

    @Transactional
    public Resource upload(String title, String description, String category,
                           MultipartFile file, String uploaderEmail) throws IOException {
        User uploader = userRepository.findByEmail(uploaderEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Resource resource = Resource.builder()
                .title(title)
                .description(description)
                .category(category)
                .fileType(file.getContentType())
                .originalFileName(file.getOriginalFilename())
                .fileData(file.getBytes())
                .fileSizeBytes(file.getSize())
                .uploadedBy(uploader)
                .build();
        return resourceRepository.save(resource);
    }

    @Transactional
    public Resource createWithBytes(String title, String description, String category,
                                    String fileName, String contentType, byte[] data, User uploader) {
        Resource resource = Resource.builder()
                .title(title)
                .description(description)
                .category(category)
                .fileType(contentType)
                .originalFileName(fileName)
                .fileData(data)
                .fileSizeBytes(data.length)
                .uploadedBy(uploader)
                .build();
        return resourceRepository.save(resource);
    }

    public void delete(Long id) {
        resourceRepository.deleteById(id);
    }
}
