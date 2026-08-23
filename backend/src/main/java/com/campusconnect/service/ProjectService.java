package com.campusconnect.service;

import com.campusconnect.entity.Project;
import com.campusconnect.entity.User;
import com.campusconnect.repository.ProjectRepository;
import com.campusconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<Project> getAll() {
        return projectRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Project> search(String q) {
        if (q == null || q.isBlank()) return getAll();
        return projectRepository.search(q);
    }

    public Project getById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + id));
    }

    @Transactional
    public Project create(Map<String, Object> body, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Project project = Project.builder()
                .title((String) body.get("title"))
                .description((String) body.get("description"))
                .techStack((String) body.get("techStack"))
                .githubUrl((String) body.get("githubUrl"))
                .liveUrl((String) body.get("liveUrl"))
                .status(Project.ProjectStatus.ACTIVE)
                .owner(owner)
                .build();
        return projectRepository.save(project);
    }

    @Transactional
    public Project update(Long id, Map<String, Object> body, String ownerEmail) {
        Project project = getById(id);
        if (!project.getOwner().getEmail().equals(ownerEmail)) {
            throw new SecurityException("Not authorized");
        }
        if (body.containsKey("title")) project.setTitle((String) body.get("title"));
        if (body.containsKey("description")) project.setDescription((String) body.get("description"));
        if (body.containsKey("techStack")) project.setTechStack((String) body.get("techStack"));
        if (body.containsKey("githubUrl")) project.setGithubUrl((String) body.get("githubUrl"));
        if (body.containsKey("liveUrl")) project.setLiveUrl((String) body.get("liveUrl"));
        return projectRepository.save(project);
    }

    @Transactional
    public void delete(Long id, String ownerEmail) {
        Project project = getById(id);
        if (!project.getOwner().getEmail().equals(ownerEmail)) {
            throw new SecurityException("Not authorized");
        }
        projectRepository.delete(project);
    }
}
