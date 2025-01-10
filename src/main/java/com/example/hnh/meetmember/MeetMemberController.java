package com.example.hnh.meetmember;

import com.example.hnh.global.config.auth.UserDetailsImpl;
import com.example.hnh.meetmember.dto.MeetMemberResponseDto;
import com.example.hnh.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
public class MeetMemberController {

    private final MeetMemberService meetMemberService;

    public MeetMemberController (MeetMemberService meetMemberService) {
        this.meetMemberService = meetMemberService;
    }

    /**
     * 모임 참여 API
     * @param groupId
     * @param meetId
     * @param userDetails
     * @return
     */
    @PostMapping("/{groupId}/meets/{meetId}/join")
    public ResponseEntity<MeetMemberResponseDto> joinMeet(
            @PathVariable Long groupId,
            @PathVariable Long meetId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User loginUser = userDetails.getUser();
        MeetMemberResponseDto responseDto = meetMemberService.joinMeet(groupId, meetId, loginUser);

        return ResponseEntity.ok(responseDto);
    }
}
