package com.example.hnh.member.dto;

import lombok.Getter;

@Getter
public class AddMemberResponseDto {

    private final Long memberId;

    private final String status;

    public AddMemberResponseDto(Long memberId, String status) {
        this.memberId = memberId;
        this.status = status;
    }
}
