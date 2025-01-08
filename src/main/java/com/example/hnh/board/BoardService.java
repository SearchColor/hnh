package com.example.hnh.board;

import com.example.hnh.board.dto.BoardResponseDto;
import com.example.hnh.board.dto.SearchAllBoardResponseDto;
import com.example.hnh.board.dto.SearchBoardResponseDto;
import com.example.hnh.board.dto.UpdateBoardResponseDto;
import com.example.hnh.global.error.errorcode.ErrorCode;
import com.example.hnh.global.error.exception.CustomException;
import com.example.hnh.global.s3.S3Service;
import com.example.hnh.member.Member;
import com.example.hnh.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        Board board = new Board(title, imagePath, detail, 0L, 0L, member.getGroup(), member);
        Board savedBoard = boardRepository.save(board);

        return new BoardResponseDto(
                savedBoard.getId(),
                savedBoard.getMember().getId(),
                savedBoard.getTitle(),
                savedBoard.getDetail(),
                savedBoard.getImagePath(),
                savedBoard.getCreatedAt());
    }

    public Page<SearchAllBoardResponseDto> getBoards(Long groupId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Board> boards = boardRepository.findAllByGroupId(groupId, pageable);

        return boards.map(board -> new SearchAllBoardResponseDto(
                board.getId(),
                board.getMember().getId(),
                board.getTitle(),
                board.getImagePath(),
                board.getView(),
                board.getLikeCount(),
                board.getCreatedAt(),
                board.getModifiedAt()));
    }

    public SearchBoardResponseDto getBoard(Long boardId) {

        //TODO:권한 처리(멤버만 볼 수 있게 설정)
        Board board = boardRepository.findByBoardIdOrElseThrow(boardId);

        return new SearchBoardResponseDto(board);
    }

    public UpdateBoardResponseDto updateBoard(Long boardId, Long userId, Long groupId, String title, String detail, MultipartFile image) throws IOException {
        Member member = memberRepository.findByUserIdAndGroupIdOrElseThrow(userId, groupId);
        Board board = boardRepository.findByBoardIdOrElseThrow(boardId);

        if(!board.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_AUTHOR);
        }

        //TODO:이미지 변경 시 기존 이미지 삭제 후 재 업로드
        String imagePath = s3Service.uploadImage(image);

        board.updateBoard(title, imagePath, detail);

        return new UpdateBoardResponseDto(
                board.getId(),
                board.getMember().getId(),
                board.getTitle(),
                board.getDetail(),
                board.getImagePath(),
                board.getModifiedAt());
    }
}