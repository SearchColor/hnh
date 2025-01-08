package com.example.hnh.user.dto;

import lombok.Getter;

@Getter
public class ResignRequestDto {

    private String password;

    public ResignRequestDto(String password){
        this.password = password;
    }
}
