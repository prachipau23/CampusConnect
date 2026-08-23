package com.campusconnect.service;

import com.campusconnect.entity.Resource;
import com.campusconnect.entity.User;
import com.campusconnect.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    public List<Resource> searchResources(String query, String category) {
        return resourceRepository.searchResources(query, category);
    }

    @Transactional
    public Resource uploadResource(User uploader, String title, String category,
                                    MultipartFile file) throws IOException {

        Resource res = new Resource();
        res.setTitle(title);
        res.setCategory(category);
        res.setUploader(uploader);
        res.setDept(uploader.getProfile() != null ? uploader.getProfile().getDepartment() : "General");

        if (file != null && !file.isEmpty()) {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            String originalName = file.getOriginalFilename();
            String ext = originalName != null && originalName.contains(".") ? originalName.substring(originalName.lastIndexOf(".") + 1).toUpperCase() : "PDF";
            String filename = "res_" + UUID.randomUUID().toString().substring(0, 8) + "_" + originalName;

            Path targetPath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), targetPath);

            res.setFilePath("/uploads/" + filename);
            res.setFormat(ext);
            long bytes = file.getSize();
            res.setSize(bytes > 1024 * 1024 ? String.format("%.1f MB", bytes / (1024.0 * 1024.0)) : String.format("%d KB", bytes / 1024));
        } else {
            res.setFilePath("#");
        }

        return resourceRepository.save(res);
    }

    @Transactional
    public Resource incrementDownload(Long resourceId) {
        Resource res = resourceRepository.findById(resourceId).orElse(null);
        if (res != null) {
            res.setDownloadCount(res.getDownloadCount() + 1);
            return resourceRepository.save(res);
        }
        return null;
    }
}
