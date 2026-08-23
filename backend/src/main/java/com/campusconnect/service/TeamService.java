package com.campusconnect.service;

import com.campusconnect.entity.*;
import com.campusconnect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private TeamJoinRequestRepository teamJoinRequestRepository;

    @Autowired
    private NotificationService notificationService;

    public List<Team> getAllTeams(String query, String status) {
        return teamRepository.searchTeams(query, status);
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    @Transactional
    public Team createTeam(User creator, String name, String projectTitle, String description,
                           String requiredSkills, int targetMemberCount, String deadline) {

        Team team = new Team();
        team.setCreatedBy(creator);
        team.setName(name);
        team.setProjectTitle(projectTitle);
        team.setDescription(description);
        team.setRequiredSkills(requiredSkills);
        team.setTargetMemberCount(targetMemberCount);
        team.setDeadline(deadline);
        team.setStatus("OPEN");
        team.setCurrentMemberCount(1);

        Team savedTeam = teamRepository.save(team);

        TeamMember leader = new TeamMember(savedTeam, creator, "Leader");
        teamMemberRepository.save(leader);

        return savedTeam;
    }

    @Transactional
    public boolean requestToJoin(Long teamId, User applicant, String message) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team == null || "CLOSED".equalsIgnoreCase(team.getStatus())) return false;

        if (teamMemberRepository.existsByTeamAndUser(team, applicant)) return false;
        if (teamJoinRequestRepository.existsByTeamAndApplicantAndStatus(team, applicant, "PENDING")) return false;

        TeamJoinRequest req = new TeamJoinRequest();
        req.setTeam(team);
        req.setApplicant(applicant);
        req.setMessage(message);
        req.setStatus("PENDING");
        teamJoinRequestRepository.save(req);

        // Send notification to team creator
        notificationService.sendNotification(
            team.getCreatedBy(),
            "New Team Join Request",
            applicant.getEmail() + " requested to join " + team.getName(),
            "👥",
            "TEAM_INVITE"
        );

        return true;
    }

    @Transactional
    public boolean handleJoinRequest(Long requestId, User leader, boolean accept) {
        TeamJoinRequest req = teamJoinRequestRepository.findById(requestId).orElse(null);
        if (req == null || !req.getStatus().equals("PENDING")) return false;

        Team team = req.getTeam();
        if (!team.getCreatedBy().getId().equals(leader.getId())) return false;

        if (accept) {
            req.setStatus("ACCEPTED");
            teamMemberRepository.save(new TeamMember(team, req.getApplicant(), "Member"));
            team.setCurrentMemberCount(team.getCurrentMemberCount() + 1);
            if (team.getCurrentMemberCount() >= team.getTargetMemberCount()) {
                team.setStatus("CLOSED");
            }
            teamRepository.save(team);

            notificationService.sendNotification(
                req.getApplicant(),
                "Team Application Accepted!",
                "Congratulations! You were accepted into team " + team.getName(),
                "🎉",
                "JOIN_APPROVAL"
            );
        } else {
            req.setStatus("REJECTED");
            notificationService.sendNotification(
                req.getApplicant(),
                "Team Application Status",
                "Your request to join team " + team.getName() + " was not accepted.",
                "ℹ️",
                "TEAM_INVITE"
            );
        }

        teamJoinRequestRepository.save(req);
        return true;
    }

    @Transactional
    public void closeRecruitment(Long teamId, User leader) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team != null && team.getCreatedBy().getId().equals(leader.getId())) {
            team.setStatus("CLOSED");
            teamRepository.save(team);
        }
    }
}
