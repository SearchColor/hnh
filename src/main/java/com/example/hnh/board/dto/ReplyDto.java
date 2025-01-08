package com.example.hnh.board.dto;

import com.example.hnh.comment.Reply;
import lombok.Getter;

@Getter
public class ReplyDto {

    private final Long replyAuthorId;

    private final String reply;

    public ReplyDto(Reply reply) {
        this.replyAuthorId = reply.getMemberId();
        this.reply = reply.getReply();
    }
}
