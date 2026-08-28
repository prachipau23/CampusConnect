package com.campusconnect.service;

import com.campusconnect.entity.Hackathon;
import com.campusconnect.repository.HackathonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HackathonService {

    private final HackathonRepository hackathonRepository;
    private final com.campusconnect.repository.TeamRepository teamRepository;
    private final com.campusconnect.repository.TeamMemberRepository teamMemberRepository;
    private final com.campusconnect.repository.UserRepository userRepository;
    private final NotificationService notificationService;

    public List<Hackathon> getAll() {
        return hackathonRepository.findAllByOrderByStartDateAsc();
    }

    public Hackathon getById(Long id) {
        return hackathonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hackathon not found: " + id));
    }

    @Transactional
    public Hackathon create(Map<String, Object> body) {
        Hackathon h = Hackathon.builder()
                .name((String) body.get("name"))
                .description((String) body.get("description"))
                .organizer((String) body.get("organizer"))
                .location((String) body.get("location"))
                .mode((String) body.getOrDefault("mode", "Online"))
                .registrationUrl((String) body.get("registrationUrl"))
                .prizeAmount(body.containsKey("prizeAmount")
                        ? new BigDecimal(body.get("prizeAmount").toString()) : BigDecimal.ZERO)
                .startDate(body.containsKey("startDate")
                        ? LocalDate.parse((String) body.get("startDate")) : null)
                .endDate(body.containsKey("endDate")
                        ? LocalDate.parse((String) body.get("endDate")) : null)
                .build();
        return hackathonRepository.save(h);
    }

    @Transactional
    public com.campusconnect.entity.Team registerTeam(Long hackathonId, Map<String, Object> body, String userEmail) {
        Hackathon h = getById(hackathonId);
        com.campusconnect.entity.User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userEmail));

        String teamName = (String) body.getOrDefault("teamName", "Team " + user.getUsername());
        String track = (String) body.getOrDefault("track", "General Track");
        Object membersObj = body.get("members");
        String membersInfo = membersObj != null ? membersObj.toString() : "";

        String desc = "Hackathon Team for " + h.getName() + " | Track: " + track;
        if (!membersInfo.isBlank()) {
            desc += " | Teammates: " + membersInfo;
        }

        com.campusconnect.entity.Team team = com.campusconnect.entity.Team.builder()
                .name(teamName)
                .description(desc)
                .maxSize(5)
                .status(com.campusconnect.entity.Team.TeamStatus.OPEN)
                .build();
        team = teamRepository.save(team);

        com.campusconnect.entity.TeamMember leader = com.campusconnect.entity.TeamMember.builder()
                .team(team)
                .user(user)
                .role(com.campusconnect.entity.TeamMember.MemberRole.LEAD)
                .build();
        teamMemberRepository.save(leader);

        // Notify the registering student
        notificationService.createNotification(
                user,
                "Successfully registered team '" + teamName + "' for " + h.getName() + "!",
                com.campusconnect.entity.Notification.EntityType.HACKATHON,
                h.getId()
        );

        return team;
    }

    @Transactional
    public void delete(Long id) {
        hackathonRepository.deleteById(id);
    }
}
