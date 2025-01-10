package com.example.hnh.meet;

import com.example.hnh.global.config.auth.UserDetailsImpl;
import com.example.hnh.meet.dto.MeetRequestDto;
import com.example.hnh.meet.dto.MeetResponseDto;
import com.example.hnh.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class MeetController {

    private final MeetService meetService;

    public MeetController(MeetService meetService) {
        this.meetService = meetService;
    }

    @PostMapping("/{groupId}/meets")
    public ResponseEntity<MeetResponseDto> createMeet(
            @PathVariable Long groupId,
            @RequestBody MeetRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User loginUser = userDetails.getUser();
        MeetResponseDto responseDto = meetService.createMeet(groupId, loginUser, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
