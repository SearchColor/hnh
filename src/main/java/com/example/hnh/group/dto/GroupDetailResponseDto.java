package com.example.hnh.group.dto;

import com.example.hnh.group.Group;
import com.example.hnh.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class GroupDetailResponseDto {

    private Long groupId;

    private String groupName;

    private String detail;

    private String imagePath;

    private String userName; // 그룹 관리자 이름

    private int memberNumber; // 멤버 수

    private List<String> members; // 멤버 이름 리스트



    public GroupDetailResponseDto(Long groupId, String groupName, String detail, String imagePath, String userName,
                                  int memberNumber, List<String> members) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.detail = detail;
        this.imagePath = imagePath;
        this.userName = userName;
        this.memberNumber = memberNumber;
        this.members = members;
    }



    public static GroupDetailResponseDto toDto(Group group, String userName, List<String> members) {
        return new GroupDetailResponseDto(
                group.getId(),
                group.getName(),
                group.getDetail(),
                group.getImagePath(),
                userName, // 관리자 이름
                members.size(), // 멤버 수
                members // 멤버 이름 리스트
        );
    }
}
