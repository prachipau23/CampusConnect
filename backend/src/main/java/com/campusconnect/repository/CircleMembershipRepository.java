package com.campusconnect.repository;

import com.campusconnect.entity.CircleMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CircleMembershipRepository extends JpaRepository<CircleMembership, Long> {
    List<CircleMembership> findByUserId(Long userId);
    List<CircleMembership> findByCircleId(Long circleId);
    Optional<CircleMembership> findByCircleIdAndUserId(Long circleId, Long userId);
    boolean existsByCircleIdAndUserId(Long circleId, Long userId);
}
