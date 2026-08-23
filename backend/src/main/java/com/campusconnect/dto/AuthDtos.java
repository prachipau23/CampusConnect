package com.campusconnect.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class AuthDtos {

    @Data
    public static class RegisterRequest {
        @NotBlank
        private String username;

        @NotBlank
        @Email
        private String email;

        @NotBlank
        @Size(min = 6, max = 100)
        private String password;

        private String fullName;
        private String role; // defaults to STUDENT if blank
    }

    @Data
    public static class LoginRequest {
        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }

    @Data
    public static class AuthResponse {
        private String token;
        private String tokenType = "Bearer";
        private Long userId;
        private String email;
        private String username;
        private String role;

        public AuthResponse(String token, Long userId, String email, String username, String role) {
            this.token = token;
            this.userId = userId;
            this.email = email;
            this.username = username;
            this.role = role;
        }
    }

    @Data
    public static class MessageResponse {
        private String message;
        public MessageResponse(String message) { this.message = message; }
    }
}
