package com.example.hnh.group;

import com.example.hnh.global.s3.S3Service;
import com.example.hnh.group.dto.GroupDetailResponseDto;
import com.example.hnh.group.dto.GroupRequestDto;
import com.example.hnh.group.dto.GroupResponseDto;
import com.example.hnh.member.Member;
import com.example.hnh.member.MemberRepository;
import com.example.hnh.member.MemberRole;
import com.example.hnh.user.User;
import com.example.hnh.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final S3Service s3Service;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    public GroupService(GroupRepository groupRepository, S3Service s3Service, UserRepository userRepository, MemberRepository memberRepository) {
        this.groupRepository = groupRepository;
        this.s3Service = s3Service;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
    }


    /**
     * 그룹 생성
     * @param userId
     * @param categoryId
     * @param groupName
     * @param detail
     * @param image
     * @return
     * @throws IOException
     */
    @Transactional
    public GroupResponseDto createGroup(User loginUser, Long categoryId, String groupName, String detail, MultipartFile image)
    throws IOException {

        // 그룹 이름 중복 확인
        if (groupRepository.existsByName(groupName)) {
            throw new IllegalArgumentException("이미 동일한 그룹 이름이 존재합니다.");
        }

        // 이미지 업로드
        String s3ImagePath = s3Service.uploadImage(image);

        // 그룹 생성
        Group group = new Group(loginUser.getId(), categoryId, groupName, detail, s3ImagePath);

        // 그룹 저장
        groupRepository.save(group);

        // 그룹 생성자를 GROUP_ADMIN으로 추가
        Member member = new Member(MemberRole.GROUP_ADMIN, loginUser, group);
        memberRepository.save(member);

        // GroupResponseDto 변환 및 반환
        return GroupResponseDto.toDto(group);

    }

    /**
     * 그룹 단건 조회 API
     * @param groupId
     * @return
     */
    public GroupDetailResponseDto findGroup(Long groupId) {
        // 그룹 조회
        Group group = groupRepository.findByGroupOrElseThrow(groupId);

        // 그룹 관리자 정보 조회
        User user = userRepository.findById(group.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 그룹 멤버 이름 리스트 조회
        List<String> members = memberRepository.findByGroupId(groupId)
                .stream()
                .map(member -> member.getUser().getName()) // `user`를 통해 이름 추출
                .collect(Collectors.toList());

        // GroupDetailResponseDto 변환 및 반환
        return GroupDetailResponseDto.toDto(group, user.getName(), members);
    }

    /**
     * 그룹 수정 API
     * @param groupId
     * @param requestDto
     * @return
     */
    public GroupResponseDto updateGroup(Long groupId, GroupRequestDto requestDto) {

        // 그룹 조회
        Group group = groupRepository.findByGroupOrElseThrow(groupId);

        // 그룹 정보 업데이트
        group.updateGroup(requestDto.getGroupName(), requestDto.getDetail(), requestDto.getImagePath());

        // 수정된 내용 저장
        groupRepository.save(group);

        // DTO로 변환하여 반환
        return GroupResponseDto.toDto(group);
    }
}
