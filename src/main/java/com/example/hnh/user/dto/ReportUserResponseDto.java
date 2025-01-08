package com.example.hnh.user.dto;

import lombok.Getter;

@Getter
public class ReportUserResponseDto {

    private final Long userId;

    private final String status;

    public ReportUserResponseDto(Long userId, String status) {
        this.userId = userId;
        this.status = status;
    }
}
