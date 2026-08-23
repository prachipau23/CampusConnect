package com.campusconnect.service;

import com.campusconnect.entity.Hackathon;
import com.campusconnect.entity.HackathonInterest;
import com.campusconnect.entity.User;
import com.campusconnect.repository.HackathonInterestRepository;
import com.campusconnect.repository.HackathonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HackathonService {

    @Autowired
    private HackathonRepository hackathonRepository;

    @Autowired
    private HackathonInterestRepository interestRepository;

    public List<Hackathon> searchHackathons(String query, String status) {
        return hackathonRepository.searchHackathons(query, status);
    }

    public Hackathon getHackathonById(Long id) {
        return hackathonRepository.findById(id).orElse(null);
    }

    public Set<Long> getInterestedHackathonIds(User user) {
        if (user == null) return Set.of();
        return interestRepository.findByUser(user).stream()
                .map(hi -> hi.getHackathon().getId())
                .collect(Collectors.toSet());
    }

    @Transactional
    public boolean registerInterest(Long hackathonId, User user, String track) {
        Hackathon h = hackathonRepository.findById(hackathonId).orElse(null);
        if (h == null) return false;

        if (!interestRepository.existsByHackathonAndUser(h, user)) {
            interestRepository.save(new HackathonInterest(h, user, track));
            h.setRegisteredCount(h.getRegisteredCount() + 1);
            hackathonRepository.save(h);
            return true;
        }
        return false;
    }

    @Transactional
    public Hackathon saveHackathon(Hackathon h) {
        return hackathonRepository.save(h);
    }

    @Transactional
    public void deleteHackathon(Long id) {
        hackathonRepository.deleteById(id);
    }
}
