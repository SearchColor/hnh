package com.example.hnh.comment;

import com.example.hnh.board.Board;
import com.example.hnh.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "comment")
    private List<Reply> replies;

    public Comment() {}

    public Comment(Long memberId, String comment, Board board) {
        this.memberId = memberId;
        this.comment = comment;
        this.board = board;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
