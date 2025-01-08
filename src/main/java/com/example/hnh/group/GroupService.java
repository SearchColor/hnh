package com.example.hnh.group;

import com.example.hnh.global.s3.S3Service;
import com.example.hnh.group.dto.GroupResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final S3Service s3Service;

    public GroupService(GroupRepository groupRepository, S3Service s3Service) {
        this.groupRepository = groupRepository;
        this.s3Service = s3Service;
    }


    public GroupResponseDto createGroup(Long userId, Long categoryId, String groupName, String detail, MultipartFile image)
    throws IOException {

        // 그룹 이름 중복 확인
        if (groupRepository.existsByName(groupName)) {
            throw new IllegalArgumentException("이미 동일한 그룹 이름이 존재합니다.");
        }

        String s3ImagePath = s3Service.uploadImage(image);

        // 그룹 생성
        Group group = new Group(userId, categoryId, groupName, detail, s3ImagePath);

        // 그룹 저장
        groupRepository.save(group);

        // GroupResponseDto 변환 및 반환
        return GroupResponseDto.toDto(group);

    }
}
