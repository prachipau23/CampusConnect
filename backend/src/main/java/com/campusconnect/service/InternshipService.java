package com.campusconnect.service;

import com.campusconnect.entity.Internship;
import com.campusconnect.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InternshipService {

    private final InternshipRepository internshipRepository;

    public List<Internship> getAll() {
        return internshipRepository.findAllByOrderByDeadlineAsc();
    }

    public Internship getById(Long id) {
        return internshipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Internship not found: " + id));
    }

    @Transactional
    public Internship create(Map<String, Object> body) {
        Internship i = Internship.builder()
                .title((String) body.get("title"))
                .company((String) body.get("company"))
                .description((String) body.get("description"))
                .location((String) body.get("location"))
                .mode((String) body.getOrDefault("mode", "Remote"))
                .duration((String) body.get("duration"))
                .applyUrl((String) body.get("applyUrl"))
                .stipend(body.containsKey("stipend")
                        ? new BigDecimal(body.get("stipend").toString()) : BigDecimal.ZERO)
                .deadline(body.containsKey("deadline")
                        ? LocalDate.parse((String) body.get("deadline")) : null)
                .build();
        return internshipRepository.save(i);
    }

    @Transactional
    public void delete(Long id) {
        internshipRepository.deleteById(id);
    }
}
