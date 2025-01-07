package com.example.hnh.member.dto;

import lombok.Getter;

@Getter
public class MemberResponseDto {

    private final Long memberId;

    private final String role;

    public MemberResponseDto(Long memberId, String role) {
        this.memberId = memberId;
        this.role = role;
    }
}
