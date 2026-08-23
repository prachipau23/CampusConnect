package com.campusconnect.service;

import com.campusconnect.entity.Project;
import com.campusconnect.entity.User;
import com.campusconnect.repository.ProjectRepository;
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
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    public List<Project> getAllProjects(String query, String status) {
        return projectRepository.searchProjects(query, status);
    }

    public List<Project> getProjectsByOwner(User user) {
        return projectRepository.findByOwner(user);
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Transactional
    public Project createProject(User owner, String title, String description, String techUsed,
                                  String githubRepo, String demoUrl, String status,
                                  MultipartFile screenshot) throws IOException {

        Project project = new Project();
        project.setOwner(owner);
        project.setTitle(title);
        project.setDescription(description);
        project.setTechUsed(techUsed);
        project.setGithubRepo(githubRepo);
        project.setDemoUrl(demoUrl);
        project.setStatus(status != null ? status : "In Development");

        if (screenshot != null && !screenshot.isEmpty()) {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            String filename = "project_" + UUID.randomUUID().toString().substring(0, 8) + "_" + screenshot.getOriginalFilename();
            Path targetPath = uploadPath.resolve(filename);
            Files.copy(screenshot.getInputStream(), targetPath);
            project.setScreenshotPath("/uploads/" + filename);
        }

        return projectRepository.save(project);
    }

    @Transactional
    public Project updateProject(Long projectId, User user, String title, String description,
                                  String techUsed, String githubRepo, String demoUrl, String status,
                                  MultipartFile screenshot) throws IOException {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        if (!project.getOwner().getId().equals(user.getId()) && !"ROLE_ADMIN".equals(user.getRole())) {
            throw new SecurityException("Not authorized to modify this project");
        }

        project.setTitle(title);
        project.setDescription(description);
        project.setTechUsed(techUsed);
        project.setGithubRepo(githubRepo);
        project.setDemoUrl(demoUrl);
        project.setStatus(status);

        if (screenshot != null && !screenshot.isEmpty()) {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            String filename = "project_" + UUID.randomUUID().toString().substring(0, 8) + "_" + screenshot.getOriginalFilename();
            Path targetPath = uploadPath.resolve(filename);
            Files.copy(screenshot.getInputStream(), targetPath);
            project.setScreenshotPath("/uploads/" + filename);
        }

        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(Long id, User user) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null && (project.getOwner().getId().equals(user.getId()) || "ROLE_ADMIN".equals(user.getRole()))) {
            projectRepository.delete(project);
        }
    }

    @Transactional
    public void toggleVerification(Long id) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
            project.setVerified(!project.isVerified());
            projectRepository.save(project);
        }
    }
}
