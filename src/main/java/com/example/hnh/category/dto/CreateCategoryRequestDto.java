package com.example.hnh.category.dto;

import lombok.Getter;
import software.amazon.awssdk.annotations.NotNull;

@Getter
public class CreateCategoryRequestDto {

    @NotNull
    private String name;

    public CreateCategoryRequestDto(String name) {
        this.name = name;
    }
}
