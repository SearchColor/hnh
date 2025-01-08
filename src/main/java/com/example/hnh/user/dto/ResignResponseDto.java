package com.example.hnh.user.dto;


import com.example.hnh.user.User;
import com.example.hnh.user.UserStatus;
import lombok.Getter;

@Getter
public class ResignResponseDto {
    private final Long userId;

    private final String name;

    private final String status;

    public ResignResponseDto(Long userId, String name , String status) {
        this.userId = userId;
        this.name = name;
        this.status = status;
    }

    public ResignResponseDto(User user){
        this.userId = user.getId();
        this.name = user.getName();
        this.status = user.getStatus();
    }
}
