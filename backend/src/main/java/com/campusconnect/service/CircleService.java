package com.campusconnect.service;

import com.campusconnect.entity.Circle;
import com.campusconnect.entity.CircleMembership;
import com.campusconnect.entity.User;
import com.campusconnect.repository.CircleMembershipRepository;
import com.campusconnect.repository.CircleRepository;
import com.campusconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CircleService {

    private final CircleRepository circleRepository;
    private final CircleMembershipRepository membershipRepository;
    private final UserRepository userRepository;

    public List<Circle> getAll() {
        return circleRepository.findAllByOrderByMemberCountDesc();
    }

    public Circle getById(Long id) {
        return circleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Circle not found: " + id));
    }

    @Transactional
    public Circle create(Map<String, Object> body) {
        Circle circle = Circle.builder()
                .name((String) body.get("name"))
                .description((String) body.get("description"))
                .category((String) body.get("category"))
                .iconEmoji((String) body.getOrDefault("iconEmoji", "🔵"))
                .build();
        return circleRepository.save(circle);
    }

    @Transactional
    public Map<String, String> join(Long circleId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (membershipRepository.existsByCircleIdAndUserId(circleId, user.getId())) {
            return Map.of("message", "Already a member");
        }
        Circle circle = getById(circleId);
        CircleMembership membership = CircleMembership.builder()
                .circle(circle)
                .user(user)
                .build();
        membershipRepository.save(membership);
        circle.setMemberCount(circle.getMemberCount() + 1);
        circleRepository.save(circle);
        return Map.of("message", "Joined circle successfully");
    }

    @Transactional
    public Map<String, String> leave(Long circleId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        CircleMembership membership = membershipRepository.findByCircleIdAndUserId(circleId, user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Not a member"));
        membershipRepository.delete(membership);
        Circle circle = getById(circleId);
        circle.setMemberCount(Math.max(0, circle.getMemberCount() - 1));
        circleRepository.save(circle);
        return Map.of("message", "Left circle");
    }
}
