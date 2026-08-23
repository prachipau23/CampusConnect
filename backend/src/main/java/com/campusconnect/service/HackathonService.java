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
    public void delete(Long id) {
        hackathonRepository.deleteById(id);
    }
}
