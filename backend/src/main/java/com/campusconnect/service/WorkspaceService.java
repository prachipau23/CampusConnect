package com.campusconnect.service;

import com.campusconnect.entity.*;
import com.campusconnect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkspaceService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private TeamDiscussionRepository discussionRepository;

    @Autowired
    private TeamTaskRepository taskRepository;

    @Autowired
    private TeamAnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean isMember(Long teamId, User user) {
        if (user == null) return false;
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team == null) return false;
        return teamMemberRepository.existsByTeamAndUser(team, user) || "ROLE_ADMIN".equals(user.getRole());
    }

    public List<TeamMember> getTeamMembers(Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);
        return team != null ? teamMemberRepository.findByTeam(team) : List.of();
    }

    public List<TeamDiscussion> getDiscussions(Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);
        return team != null ? discussionRepository.findByTeamOrderByCreatedAtDesc(team) : List.of();
    }

    public List<TeamTask> getTasks(Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);
        return team != null ? taskRepository.findByTeam(team) : List.of();
    }

    public List<TeamAnnouncement> getAnnouncements(Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);
        return team != null ? announcementRepository.findByTeamOrderByCreatedAtDesc(team) : List.of();
    }

    @Transactional
    public TeamDiscussion addDiscussion(Long teamId, User author, String content) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team == null) return null;

        TeamDiscussion d = new TeamDiscussion();
        d.setTeam(team);
        d.setAuthor(author);
        d.setContent(content);
        return discussionRepository.save(d);
    }

    @Transactional
    public TeamTask addTask(Long teamId, String title, Long assignedUserId, String dueDate) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team == null) return null;

        TeamTask task = new TeamTask();
        task.setTeam(team);
        task.setTitle(title);
        task.setDueDate(dueDate);
        task.setStatus("TODO");

        if (assignedUserId != null) {
            userRepository.findById(assignedUserId).ifPresent(task::setAssignedTo);
        }

        return taskRepository.save(task);
    }

    @Transactional
    public void updateTaskStatus(Long taskId, String status) {
        TeamTask task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            task.setStatus(status);
            taskRepository.save(task);
        }
    }

    @Transactional
    public TeamAnnouncement addAnnouncement(Long teamId, User author, String title, String content) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team == null) return null;

        TeamAnnouncement a = new TeamAnnouncement();
        a.setTeam(team);
        a.setAuthor(author);
        a.setTitle(title);
        a.setContent(content);
        return announcementRepository.save(a);
    }
}
