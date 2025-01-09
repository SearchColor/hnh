package com.example.hnh.user.dto;

import lombok.Getter;

@Getter
public class ReportGroupResponseDto {

    private final Long groupId;

    private final String status;

    public ReportGroupResponseDto(Long groupId, String status) {
        this.groupId = groupId;
        this.status = status;
    }
}
