package com.example.hnh.interestgroup.dto;

import com.example.hnh.group.Group;
import lombok.Getter;

@Getter
public class InterestGroupResponseDto {

    private Long groupId;

    private String groupName;

    private String imagePath;

    public InterestGroupResponseDto(Long groupId, String groupName, String imagePath) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.imagePath = imagePath;
    }

    public static InterestGroupResponseDto toDto(Group group) {
        return new InterestGroupResponseDto(
                group.getId(),
                group.getName(),
                group.getImagePath()
        );
    }
}
