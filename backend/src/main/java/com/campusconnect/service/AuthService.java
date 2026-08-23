package com.campusconnect.service;

import com.campusconnect.dto.AuthDtos.*;
import com.campusconnect.entity.StudentProfile;
import com.campusconnect.entity.User;
import com.campusconnect.entity.User.Role;
import com.campusconnect.repository.StudentProfileRepository;
import com.campusconnect.repository.UserRepository;
import com.campusconnect.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final StudentProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public MessageResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }

        Role role = Role.STUDENT;
        if (req.getRole() != null && !req.getRole().isBlank()) {
            try { role = Role.valueOf(req.getRole().toUpperCase()); }
            catch (IllegalArgumentException ignored) {}
        }

        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .role(role)
                .build();
        user = userRepository.save(user);

        // Auto-create student profile
        String initials = req.getFullName() != null && req.getFullName().length() >= 2
                ? req.getFullName().substring(0, 2).toUpperCase()
                : req.getUsername().substring(0, Math.min(2, req.getUsername().length())).toUpperCase();

        StudentProfile profile = StudentProfile.builder()
                .user(user)
                .fullName(req.getFullName() != null ? req.getFullName() : req.getUsername())
                .avatarInitials(initials)
                .build();
        profileRepository.save(profile);

        return new MessageResponse("User registered successfully");
    }

    public AuthResponse login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        String token = tokenProvider.generateToken(auth);
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new AuthResponse(token, user.getId(), user.getEmail(), user.getUsername(), user.getRole().name());
    }
}
