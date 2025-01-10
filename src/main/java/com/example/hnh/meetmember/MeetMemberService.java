package com.example.hnh.meetmember;

import com.example.hnh.meet.Meet;
import com.example.hnh.meet.MeetRepository;
import com.example.hnh.meetmember.dto.MeetDetailsResponseDto;
import com.example.hnh.meetmember.dto.MeetMemberResponseDto;
import com.example.hnh.member.MemberRepository;
import com.example.hnh.user.User;
import com.example.hnh.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetMemberService {

    private final MeetRepository meetRepository;
    private final MemberRepository memberRepository;
    private final MeetMemberRepository meetMemberRepository;
    private final UserRepository userRepository;

    public MeetMemberService(MeetRepository meetRepository, MemberRepository memberRepository, MeetMemberRepository meetMemberRepository, UserRepository userRepository) {
        this.meetRepository = meetRepository;
        this.memberRepository = memberRepository;
        this.meetMemberRepository = meetMemberRepository;
        this.userRepository = userRepository;
    }

    /**
     * 모임 참여 API
     * @param groupId
     * @param meetId
     * @param loginUser
     * @return
     */
    public MeetMemberResponseDto joinMeet(Long groupId, Long meetId, User loginUser) {
        // 모임 조회
        Meet meet = meetRepository.findByMeetOrElseThrow(meetId);

        // 모임이 그룹에 속해 있는지 확인
        if (!meet.getGroup().getId().equals(groupId)) {
            throw new IllegalArgumentException("모임이 해당 그룹에 속해 있지 않습니다.");
        }

        // 사용자가 그룹에 속해 있는지 확인
        memberRepository.findByUserIdAndGroupIdOrElseThrow(loginUser.getId(), groupId);

        // 사용자가 이미 모임에 참여했는지 확인
        if (meetMemberRepository.existsByMemberIdAndMeetId(loginUser.getId(), meetId)) {
            throw new IllegalArgumentException("이미 모임에 참여 중입니다.");
        }

        // 모임 멤버 생성 및 저장
        MeetMember meetMember = new MeetMember(loginUser.getId(), meet);
        meetMember = meetMemberRepository.save(meetMember);

        // 응답 DTO 생성
        return new MeetMemberResponseDto(meetMember.getId(), "active");
    }


    /**
     * 모임 멤버 조회 API
     * @param groupId
     * @param meetId
     * @return
     */
    public MeetDetailsResponseDto findMeetMember(Long groupId, Long meetId) {
        // 모임 조회
        Meet meet = meetRepository.findByMeetOrElseThrow(meetId);

        // 그룹에 속한 모임인지 확인
        if (!meet.getGroup().getId().equals(groupId)) {
            throw new IllegalArgumentException("모임이 해당 그룹에 속하지 않습니다.");
        }

        // 모임 멤버 ID 조회
        List<Long> meetMemberIds = meetMemberRepository.findByMeetId(meetId)
                .stream()
                .map(MeetMember::getMemberId)
                .collect(Collectors.toList());

        // 멤버 이름 조회
        List<String> meetMemberNames = userRepository.findNamesByIds(meetMemberIds);

        // DTO 생성 및 반환
        return MeetDetailsResponseDto.toDto(meet, meetMemberNames);
    }
}
