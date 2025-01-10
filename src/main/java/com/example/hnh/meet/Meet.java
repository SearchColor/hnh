package com.example.hnh.meet;


import com.example.hnh.global.BaseEntity;
import com.example.hnh.group.Group;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "meet")
public class Meet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = false)
    private LocalDateTime dueAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    public Meet() {}

    public Meet(String title, String detail, LocalDateTime dueAt, Group group, Long memberId) {
        this.title = title;
        this.detail = detail;
        this.dueAt = dueAt;
        this.group = group;
        this.memberId = memberId;
    }
}
