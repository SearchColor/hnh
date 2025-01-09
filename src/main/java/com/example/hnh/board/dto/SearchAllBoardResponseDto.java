package com.example.hnh.board.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SearchAllBoardResponseDto {

    private final Long boardId;

    private final Long memberId;

    private final String title;

    private final String imagePath;

    private final Long view;

    private final Long like;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public SearchAllBoardResponseDto(Long boardId, Long memberId, String title, String imagePath, Long view, Long like, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.title = title;
        this.imagePath = imagePath;
        this.view = view;
        this.like = like;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
