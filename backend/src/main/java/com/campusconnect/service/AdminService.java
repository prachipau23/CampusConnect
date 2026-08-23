package com.campusconnect.service;

import com.campusconnect.entity.SystemAnnouncement;
import com.campusconnect.entity.User;
import com.campusconnect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CircleRepository circleRepository;

    @Autowired
    private HackathonRepository hackathonRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private SystemAnnouncementRepository announcementRepository;

    @Autowired
    private NotificationService notificationService;

    public Map<String, Object> getPlatformStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", userRepository.count());
        stats.put("totalProjects", projectRepository.count());
        stats.put("verifiedProjects", projectRepository.countByVerifiedTrue());
        stats.put("activeTeams", teamRepository.count());
        stats.put("totalCircles", circleRepository.count());
        stats.put("totalHackathons", hackathonRepository.count());
        stats.put("totalInternships", internshipRepository.count());
        stats.put("totalResources", resourceRepository.count());
        return stats;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void toggleUserActiveStatus(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setActive(!user.isActive());
            userRepository.save(user);
        }
    }

    @Transactional
    public SystemAnnouncement publishAnnouncement(String title, String content, String priority) {
        SystemAnnouncement sa = new SystemAnnouncement();
        sa.setTitle(title);
        sa.setContent(content);
        sa.setPriority(priority != null ? priority : "NORMAL");
        SystemAnnouncement saved = announcementRepository.save(sa);

        notificationService.sendNotificationToAll(
            "📢 Campus Announcement: " + title,
            content,
            "📢",
            "ANNOUNCEMENT"
        );

        return saved;
    }

    public List<SystemAnnouncement> getSystemAnnouncements() {
        return announcementRepository.findAllByOrderByCreatedAtDesc();
    }
}
