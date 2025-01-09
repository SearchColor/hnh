package com.example.hnh.comment;

import com.example.hnh.comment.dto.ReplyRequestDto;
import com.example.hnh.comment.dto.ReplyResponseDto;
import com.example.hnh.global.MessageResponseDto;
import com.example.hnh.global.config.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments/{commentId}/replies")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<ReplyResponseDto> addReply(
            @PathVariable Long commentId,
            @RequestBody ReplyRequestDto dto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();

        ReplyResponseDto replyResponseDto = replyService.addReply(userId, commentId, dto.getReply());

        return new ResponseEntity<>(replyResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{replyId}")
    public ResponseEntity<ReplyResponseDto> updateReply(
            @PathVariable Long replyId,
            @RequestBody ReplyRequestDto dto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();

        ReplyResponseDto replyResponseDto = replyService.updateReply(userId, replyId, dto.getReply());

        return new ResponseEntity<>(replyResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<MessageResponseDto> deleteReply(
            @PathVariable Long replyId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();

        MessageResponseDto replyResponseDto = replyService.deleteReply(userId, replyId);

        return new ResponseEntity<>(replyResponseDto, HttpStatus.OK);
    }
}
