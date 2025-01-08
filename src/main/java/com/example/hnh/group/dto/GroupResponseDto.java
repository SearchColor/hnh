package com.example.hnh.group.dto;

import com.example.hnh.group.Group;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GroupResponseDto {

    private Long groupId; // 그룹 식별자 ID

    private Long userId; // 그룹 생성자 ID

    private Long categoryId; // 카테고리 ID

    private String groupName; // 그룹명

    private String detail; // 그룹 설명

    private String imagePath; // 이미지 파일

    private LocalDateTime createdAt; // 생성일



    public GroupResponseDto(Long groupId, Long userId, Long categoryId, String groupName, String detail,
                            String imagePath, LocalDateTime createdAt) {
        this.groupId = groupId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.groupName = groupName;
        this.detail = detail;
        this.imagePath = imagePath;
        this.createdAt = createdAt;
    }

    public static GroupResponseDto toDto(Group group) {
        return new GroupResponseDto(
                group.getId(),
                group.getUserId(),
                group.getCategoryId(),
                group.getName(),
                group.getDetail(),
                group.getImagePath(),
                group.getCreatedAt()
        );
    }
}
