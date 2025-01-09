package com.example.hnh.comment;

import com.example.hnh.comment.dto.ReplyResponseDto;
import com.example.hnh.member.Member;
import com.example.hnh.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ReplyResponseDto addReply(Long userId, Long commentId, String reply) {
        Comment comment = commentRepository.findByCommentIdOrElseThrow(commentId);
        Member member = memberRepository.findByUserIdAndGroupIdOrElseThrow(comment.getBoard().getGroup().getId(), userId);

        Reply newReply = new Reply(reply, member.getId(), comment);
        Reply savedReply = replyRepository.save(newReply);

        return new ReplyResponseDto(savedReply.getId(), savedReply.getMemberId(), savedReply.getReply());
    }
}
