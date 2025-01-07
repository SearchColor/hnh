package com.example.hnh.member;

import com.example.hnh.global.MessageResponseDto;
import com.example.hnh.global.error.errorcode.ErrorCode;
import com.example.hnh.global.error.exception.CustomException;
import com.example.hnh.group.Group;
import com.example.hnh.group.GroupRepository;
import com.example.hnh.member.dto.AddMemberResponseDto;
import com.example.hnh.member.dto.MemberResponseDto;
import com.example.hnh.user.User;
import com.example.hnh.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    /**
     * 멤버 추가
     *
     * @param userId   유저 id
     * @param groupId   가입하는 그룹 id
     * @return AddMemberResponseDto
     */
    @Transactional
    public AddMemberResponseDto addMember(Long userId, Long groupId) {
        //TODO : Repositoy 메서드에 따라 수정
        User user = userRepository.findById(userId).orElse(null);
        Group group = groupRepository.findById(groupId).orElse(null);

        Optional<Member> optionalMember = memberRepository.findByUserIdAndGroupId(userId, groupId);
        if(optionalMember.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_MEMBER);
        }

        Member member = new Member("MEMBER", user, group);
        member.setStatus("pending");
        Member savedMember = memberRepository.save(member);

        return new AddMemberResponseDto(savedMember.getId(), savedMember.getStatus());
    }

    /**
     * 멤버 승인
     *
     * @param userId   유저 id(그룹 관리자)
     * @param memberId   승인되는 멤버 id
     * @return AddMemberResponseDto
     */
    @Transactional
    public AddMemberResponseDto approveMember(Long userId, Long memberId) {
        //TODO : Repositoy 메서드에 따라 수정, 권한 체킹(그룹 관리자만 멤버 승인 가능) -> security 권한 처리시 삭제
        User user = userRepository.findById(userId).orElseThrow(() ->  new CustomException(ErrorCode.USER_NOT_FOUND));
        if(user.getAuth().equals("ADMIN")){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        //해당 멤버를 찾아서 상태 변경(PENDING -> ACTIVITY)
        Member member = memberRepository.findByMemberId(memberId);
        member.setStatus("activity");

        return new AddMemberResponseDto(member.getId(), member.getStatus());
    }

    /**
     * 멤버 상태 변경
     *
     * @param userId   유저 id(그룹 관리자)
     * @param memberId   변경되는 멤버 id
     * @param role   변경하는 상태값
     * @return MemberResponseDto
     */
    @Transactional
    public MemberResponseDto updateStatusMember(Long userId, Long memberId, String role) {
        //TODO : Repositoy 메서드에 따라 수정, 권한 체킹(그룹 관리자만 멤버 변경 가능) -> security 권한 처리시 삭제
        User user = userRepository.findById(userId).orElseThrow(() ->  new CustomException(ErrorCode.USER_NOT_FOUND));
        if(user.getAuth().equals("ADMIN")){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        Member member = memberRepository.findByMemberId(memberId);
        member.setRole(role);

        return new MemberResponseDto(member.getId(), member.getRole());
    }


    /**
     * 멤버 전체 조회
     *
     * @param groupId   그룹 id
     * @return List<MemberResponseDto>
     */
    public List<MemberResponseDto> getAllMembers(Long groupId) {
        List<Member> members = memberRepository.findByGroupId(groupId);

        return members.stream()
                .map(member -> new MemberResponseDto(
                        member.getId(),
                        member.getRole())).collect(Collectors.toList());
    }

    /**
     * 멤버 탈퇴
     *
     * @param userId   유저 id
     * @param groupId   그룹 id
     * @return MessageResponseDto
     */
    @Transactional
    public MessageResponseDto deleteMember(Long userId, Long groupId) {
        Member member = memberRepository.findByUserIdAndGroupIdOrElseThrow(userId, groupId);

        member.setStatus("deleted");

        return new MessageResponseDto("그룹 탈퇴가 완료되었습니다.");
    }
}
