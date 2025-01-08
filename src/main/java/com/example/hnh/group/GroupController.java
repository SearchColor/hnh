package com.example.hnh.group;


import com.example.hnh.global.config.auth.UserDetailsImpl;
import com.example.hnh.group.dto.GroupRequestDto;
import com.example.hnh.group.dto.GroupResponseDto;
import com.example.hnh.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }


    @PostMapping
    public ResponseEntity<GroupResponseDto> createGroup (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                         @RequestPart("requestDto") GroupRequestDto requestDto,
                                                         @RequestPart("image") MultipartFile image) throws IOException {
        User loginUser = userDetails.getUser();

        GroupResponseDto groupResponseDto = groupService.createGroup(
                loginUser.getId(),
                requestDto.getCategoryId(),
                requestDto.getGroupName(),
                requestDto.getDetail(),
                image);

        return new ResponseEntity<>(groupResponseDto, HttpStatus.CREATED);
    }
}
