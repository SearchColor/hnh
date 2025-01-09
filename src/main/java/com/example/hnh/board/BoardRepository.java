package com.example.hnh.board;

import com.example.hnh.global.error.errorcode.ErrorCode;
import com.example.hnh.global.error.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllByGroupId(Long groupId, Pageable pageable);

    default Board findByBoardIdOrElseThrow(Long boardId){
        return findById(boardId).orElseThrow(()->new CustomException(ErrorCode.BOARD_NOT_FOUND));
    }
}
