package com.example.hnh.group.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GroupRequestDto {

    @NotNull
    private Long categoryId;

    @NotNull
    private String groupName;

    @NotNull
    private String detail;

    private String imagePath;


    public GroupRequestDto(Long categoryId, String groupName, String detail, String imagePath) {
        this.categoryId = categoryId;
        this.groupName = groupName;
        this.detail = detail;
        this.imagePath = imagePath;
    }
}
