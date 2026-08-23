package com.campusconnect.service;

import com.campusconnect.entity.Team;
import com.campusconnect.entity.TeamMember;
import com.campusconnect.entity.User;
import com.campusconnect.entity.WorkspacePost;
import com.campusconnect.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository memberRepository;
    private final WorkspacePostRepository workspacePostRepository;
    private final UserRepository userRepository;

    public List<Team> getAll() {
        return teamRepository.findAllByOrderByCreatedAtDesc();
    }

    public Team getById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team not found: " + id));
    }

    @Transactional
    public Team create(Map<String, Object> body, String leaderEmail) {
        User leader = userRepository.findByEmail(leaderEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        int maxSize = body.containsKey("maxSize") ? (Integer) body.get("maxSize") : 5;
        Team team = Team.builder()
                .name((String) body.get("name"))
                .description((String) body.get("description"))
                .maxSize(maxSize)
                .status(Team.TeamStatus.OPEN)
                .build();
        team = teamRepository.save(team);

        TeamMember leadMember = TeamMember.builder()
                .team(team)
                .user(leader)
                .role(TeamMember.MemberRole.LEAD)
                .build();
        memberRepository.save(leadMember);
        return team;
    }

    @Transactional
    public Map<String, String> join(Long teamId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Team team = getById(teamId);

        if (memberRepository.existsByTeamIdAndUserId(teamId, user.getId())) {
            return Map.of("message", "Already a member");
        }
        if (team.getStatus() == Team.TeamStatus.CLOSED) {
            return Map.of("message", "Team is closed");
        }
        long currentSize = memberRepository.countByTeamId(teamId);
        if (currentSize >= team.getMaxSize()) {
            return Map.of("message", "Team is full");
        }

        TeamMember member = TeamMember.builder().team(team).user(user).build();
        memberRepository.save(member);

        if (currentSize + 1 >= team.getMaxSize()) {
            team.setStatus(Team.TeamStatus.CLOSED);
            teamRepository.save(team);
        }
        return Map.of("message", "Joined team successfully");
    }

    @Transactional
    public Map<String, String> leave(Long teamId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        TeamMember member = memberRepository.findByTeamIdAndUserId(teamId, user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Not a member"));
        memberRepository.delete(member);
        Team team = getById(teamId);
        if (team.getStatus() == Team.TeamStatus.CLOSED) {
            team.setStatus(Team.TeamStatus.OPEN);
            teamRepository.save(team);
        }
        return Map.of("message", "Left team");
    }

    public List<WorkspacePost> getWorkspacePosts(Long teamId) {
        return workspacePostRepository.findByTeamIdOrderByCreatedAtDesc(teamId);
    }

    @Transactional
    public WorkspacePost addWorkspacePost(Long teamId, String content, String postType, String authorEmail) {
        User author = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Team team = getById(teamId);
        WorkspacePost post = WorkspacePost.builder()
                .team(team)
                .author(author)
                .content(content)
                .postType(postType != null ? postType : "UPDATE")
                .build();
        return workspacePostRepository.save(post);
    }
}
