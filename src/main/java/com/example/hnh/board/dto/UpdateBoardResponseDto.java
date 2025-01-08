package com.example.hnh.board.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateBoardResponseDto {

    private final Long boardId;

    private final Long memberId;

    private final String title;

    private final String detail;

    private final String imagePath;

    private final LocalDateTime modifiedAt;

    public UpdateBoardResponseDto(Long boardId, Long memberId, String title, String detail, String imagePath, LocalDateTime modifiedAt) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.title = title;
        this.detail = detail;
        this.imagePath = imagePath;
        this.modifiedAt = modifiedAt;
    }
}
