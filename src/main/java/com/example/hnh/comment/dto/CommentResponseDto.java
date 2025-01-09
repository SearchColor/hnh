package com.example.hnh.comment.dto;

import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long commentId;

    private final Long authorId;

    private final String comment;

    public CommentResponseDto(Long commentId, Long authorId, String comment) {
        this.commentId = commentId;
        this.authorId = authorId;
        this.comment = comment;
    }
}
