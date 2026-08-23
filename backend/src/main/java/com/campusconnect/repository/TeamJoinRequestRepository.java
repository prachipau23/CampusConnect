package com.campusconnect.repository;

import com.campusconnect.entity.Team;
import com.campusconnect.entity.TeamJoinRequest;
import com.campusconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamJoinRequestRepository extends JpaRepository<TeamJoinRequest, Long> {
    List<TeamJoinRequest> findByTeam(Team team);
    List<TeamJoinRequest> findByTeamAndStatus(Team team, String status);
    Optional<TeamJoinRequest> findByTeamAndApplicant(Team team, User applicant);
    boolean existsByTeamAndApplicantAndStatus(Team team, User applicant, String status);
}
