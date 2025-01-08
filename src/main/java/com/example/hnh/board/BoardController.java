package com.example.hnh.board;

import com.example.hnh.board.dto.BoardRequestDto;
import com.example.hnh.board.dto.BoardResponseDto;
import com.example.hnh.board.dto.SearchAllBoardResponseDto;
import com.example.hnh.global.config.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups/{groupId}/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(
            @PathVariable Long groupId,
            @RequestPart("dto") BoardRequestDto dto,
            @RequestPart("image") MultipartFile image,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        Long userId = userDetails.getUser().getId();

        BoardResponseDto boardResponseDto = boardService.createBoard(userId, groupId, dto.getTitle(), dto.getDetail(), image);

        return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<SearchAllBoardResponseDto>> getAllBoards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable Long groupId) {

        Page<SearchAllBoardResponseDto> boardResponseDtoPage = boardService.getBoards(groupId, page, size);

        return new ResponseEntity<>(boardResponseDtoPage, HttpStatus.OK);
    }
}
