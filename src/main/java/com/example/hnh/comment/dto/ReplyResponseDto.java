package com.example.hnh.comment.dto;

import lombok.Getter;

@Getter
public class ReplyResponseDto {

    private final Long replyId;

    private final Long replyAuthorId;

    private final String reply;

    public ReplyResponseDto(Long replyId, Long replyAuthorId, String reply) {
        this.replyId = replyId;
        this.replyAuthorId = replyAuthorId;
        this.reply = reply;
    }
}
