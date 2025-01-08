package com.example.hnh.member;

import com.example.hnh.global.MessageResponseDto;
import com.example.hnh.global.config.auth.UserDetailsImpl;
import com.example.hnh.member.dto.AddMemberRequestDto;
import com.example.hnh.member.dto.AddMemberResponseDto;
import com.example.hnh.member.dto.MemberResponseDto;
import com.example.hnh.member.dto.UpdateStatusMemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups/{groupId}/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<AddMemberResponseDto> addMember(
            @PathVariable("groupId") Long groupId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        Long userId = userDetails.getUser().getId();

        AddMemberResponseDto addMemberResponseDto = memberService.addMember(userId, groupId);

        return new ResponseEntity<>(addMemberResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/approve")
    public ResponseEntity<AddMemberResponseDto> approveMember(
            @RequestBody AddMemberRequestDto dto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        Long userId = userDetails.getUser().getId();

        AddMemberResponseDto addMemberResponseDto = memberService.approveMember(userId, dto.getMemberId());

        return new ResponseEntity<>(addMemberResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateStatusMember(
            @PathVariable("memberId") Long memberId,
            @RequestBody UpdateStatusMemberRequestDto dto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();

        MemberResponseDto memberResponseDto = memberService.updateStatusMember(userId, memberId, dto.getRole());

        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getMembers(@PathVariable("groupId") Long groupId){

        List<MemberResponseDto> members = memberService.getAllMembers(groupId);

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<MessageResponseDto> deleteMember(
            @PathVariable("groupId") Long groupId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();

        MessageResponseDto messageResponseDto = memberService.deleteMember(userId, groupId);

        return new ResponseEntity<>(messageResponseDto, HttpStatus.OK);
    }

}
