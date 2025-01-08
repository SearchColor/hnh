package com.example.hnh.user.dto;

import lombok.Getter;
import software.amazon.awssdk.annotations.NotNull;

@Getter
public class ReportUserRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private String status;

    public ReportUserRequestDto(Long userId, String status) {
        this.userId = userId;
        this.status = status;
    }
}
