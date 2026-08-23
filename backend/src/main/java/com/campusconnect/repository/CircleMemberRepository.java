package com.campusconnect.repository;

import com.campusconnect.entity.Circle;
import com.campusconnect.entity.CircleMember;
import com.campusconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CircleMemberRepository extends JpaRepository<CircleMember, Long> {
    Optional<CircleMember> findByCircleAndUser(Circle circle, User user);
    boolean existsByCircleAndUser(Circle circle, User user);
    List<CircleMember> findByUser(User user);
    void deleteByCircleAndUser(Circle circle, User user);
}
