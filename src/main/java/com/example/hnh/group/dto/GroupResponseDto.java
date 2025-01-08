package com.example.hnh.group.dto;

import com.example.hnh.group.Group;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GroupResponseDto {

    private Long groupId;

    private Long categoryId;

    private String groupName;

    private String detail;

    private String imagePath;

    private LocalDateTime createdAt;



    public GroupResponseDto(Long groupId, Long categoryId, String groupName, String detail,
                            String imagePath, LocalDateTime createdAt) {
        this.groupId = groupId;
        this.categoryId = categoryId;
        this.groupName = groupName;
        this.detail = detail;
        this.imagePath = imagePath;
        this.createdAt = createdAt;
    }

    public static GroupResponseDto toDto(Group group) {
        return new GroupResponseDto(
                group.getId(),
                group.getCategoryId(),
                group.getName(),
                group.getDetail(),
                group.getImagePath(),
                group.getCreatedAt()
        );
    }
}
