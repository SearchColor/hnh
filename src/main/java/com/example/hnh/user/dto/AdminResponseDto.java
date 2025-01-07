package com.example.hnh.user.dto;

import com.example.hnh.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminResponseDto {

    private final Long userId;

    private final String email;

    private final String name;

    private final String auth;

    private final String status;

    private final LocalDateTime createdAt;

    public AdminResponseDto(Long userId, String email, String name, String auth, String status, LocalDateTime createdAt) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.auth = auth;
        this.status = status;
        this.createdAt = createdAt;
    }

    public AdminResponseDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.auth = user.getAuth();
        this.status = user.getStatus();
        this.createdAt = user.getCreatedAt();
    }
}
