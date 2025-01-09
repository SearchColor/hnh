package com.example.hnh.comment;

import com.example.hnh.global.error.errorcode.ErrorCode;
import com.example.hnh.global.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    default Reply findByReplyIdOrElseThrow(Long replyId) {
        return findById(replyId).orElseThrow(
                () -> new CustomException(ErrorCode.REPLY_NOT_FOUND));
    }
}