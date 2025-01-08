package com.example.hnh.board;

import com.example.hnh.board.dto.BoardResponseDto;
import com.example.hnh.global.s3.S3Service;
import com.example.hnh.member.Member;
import com.example.hnh.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Transactional
    public BoardResponseDto createBoard(Long userId, Long groupId, String title, String detail, MultipartFile image) throws IOException {
        Member member = memberRepository.findByUserIdAndGroupIdOrElseThrow(userId, groupId);

        String imagePath = s3Service.uploadImage(image);

        Board board = new Board(title, imagePath, detail, 0L, member.getGroup(), member);
        Board savedBoard = boardRepository.save(board);

        return new BoardResponseDto(
                savedBoard.getId(),
                savedBoard.getMember().getId(),
                savedBoard.getTitle(),
                savedBoard.getDetail(),
                savedBoard.getImagePath(),
                savedBoard.getCreatedAt());
    }
}