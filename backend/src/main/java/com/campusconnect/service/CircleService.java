package com.campusconnect.service;

import com.campusconnect.entity.Circle;
import com.campusconnect.entity.CircleMember;
import com.campusconnect.entity.User;
import com.campusconnect.repository.CircleMemberRepository;
import com.campusconnect.repository.CircleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CircleService {

    @Autowired
    private CircleRepository circleRepository;

    @Autowired
    private CircleMemberRepository circleMemberRepository;

    public List<Circle> getAllCircles() {
        return circleRepository.findAll();
    }

    public Circle getCircleById(Long id) {
        return circleRepository.findById(id).orElse(null);
    }

    public Set<Long> getJoinedCircleIds(User user) {
        if (user == null) return Set.of();
        return circleMemberRepository.findByUser(user).stream()
                .map(cm -> cm.getCircle().getId())
                .collect(Collectors.toSet());
    }

    @Transactional
    public void joinCircle(Long circleId, User user) {
        Circle circle = circleRepository.findById(circleId).orElse(null);
        if (circle != null && !circleMemberRepository.existsByCircleAndUser(circle, user)) {
            CircleMember cm = new CircleMember(circle, user);
            circleMemberRepository.save(cm);
            circle.setMemberCount(circle.getMemberCount() + 1);
            circleRepository.save(circle);
        }
    }

    @Transactional
    public void leaveCircle(Long circleId, User user) {
        Circle circle = circleRepository.findById(circleId).orElse(null);
        if (circle != null && circleMemberRepository.existsByCircleAndUser(circle, user)) {
            circleMemberRepository.deleteByCircleAndUser(circle, user);
            circle.setMemberCount(Math.max(0, circle.getMemberCount() - 1));
            circleRepository.save(circle);
        }
    }

    @Transactional
    public Circle createCircle(String name, String category, String description, String icon) {
        Circle c = new Circle(name, category, description, icon != null ? icon : "🌐");
        return circleRepository.save(c);
    }
}
