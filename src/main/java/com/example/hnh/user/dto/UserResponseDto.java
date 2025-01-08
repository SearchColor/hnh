package com.example.hnh.user.dto;


import com.example.hnh.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;

    private final String name;

    private final String email;


    public UserResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserResponseDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
