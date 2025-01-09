package com.example.hnh.comment;

import com.example.hnh.board.Board;
import com.example.hnh.board.BoardRepository;
import com.example.hnh.comment.dto.CommentResponseDto;
import com.example.hnh.member.Member;
import com.example.hnh.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public CommentResponseDto addComment(Long userId, Long boardId, String comment) {
        Board board = boardRepository.findByBoardIdOrElseThrow(boardId);
        Member member = memberRepository.findByUserIdAndGroupIdOrElseThrow(board.getMember().getGroup().getId(), userId);

        Comment newComment = new Comment(member.getId(), comment, board);
        Comment savedComment = commentRepository.save(newComment);

        return new CommentResponseDto(savedComment.getId(), savedComment.getMemberId(), savedComment.getComment());
    }
}
