package com.example.hnh.meet;

import com.example.hnh.group.Group;
import com.example.hnh.group.GroupRepository;
import com.example.hnh.meet.dto.MeetRequestDto;
import com.example.hnh.meet.dto.MeetResponseDto;
import com.example.hnh.member.Member;
import com.example.hnh.member.MemberRepository;
import com.example.hnh.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final MeetRepository meetRepository;

    public MeetService(GroupRepository groupRepository, MemberRepository memberRepository, MeetRepository meetRepository) {
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
        this.meetRepository = meetRepository;
    }

    /**
     * 모임 생성 API
     * @param groupId
     * @param loginUser
     * @param requestDto
     * @return
     */
    public MeetResponseDto createMeet(Long groupId, User loginUser, MeetRequestDto requestDto) {

        // 그룹 존재 여부 확인
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        // 사용자가 해당 그룹에 속해 있는지 확인
        Member member = memberRepository.findByUserIdAndGroupIdOrElseThrow(loginUser.getId(), groupId);

        LocalDateTime currentDateTime = LocalDateTime.now();

        // dueAt이 createdAt보다 늦어야 함
        if (requestDto.getDueAt().isBefore(currentDateTime)) {
            throw new IllegalArgumentException("모임 종료 날짜(dueAt)는 현재 날짜 이후여야 합니다.");
        }

        // Meet 엔티티 생성
        Meet meet = new Meet(
                requestDto.getMeetTitle(),
                requestDto.getDetail(),
                requestDto.getDueAt(),
                group,
                loginUser.getId() // 로그인한 사용자 ID를 memberId로 저장
        );

        // 저장 후 DTO 변환
        meet = meetRepository.save(meet);
        return MeetResponseDto.toDto(meet);
    }


    /**
     * 해당 그룹 모든 모임 조회 API
     * @param groupId
     * @return
     */
    public List<MeetResponseDto> findMeets(Long groupId) {

        // 해당 그룹이 존재하는지 확인
        Group group = groupRepository.findByGroupOrElseThrow(groupId);

        // 그룹 ID를 기준으로 모든 모임 조회
        List<Meet> meets = meetRepository.findAllByGroupId(groupId);

        // Meet 엔티티를 DTO로 변환하여 반환
        List<MeetResponseDto> responseDtos = new ArrayList<>();
        for (Meet meet : meets) {
            responseDtos.add(MeetResponseDto.toDto(meet));
        }

        return responseDtos;
    }



}
