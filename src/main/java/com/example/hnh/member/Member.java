package com.example.hnh.member;

import com.example.hnh.global.BaseEntity;
import com.example.hnh.group.Group;
import com.example.hnh.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "member")
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'MEMBER'")
    private MemberRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public Member() {}

    public Member(MemberRole role, User user, Group group) {
        this.role = role;
        this.user = user;
        this.group = group;
    }

    public void setRole(MemberRole role) {
        this.role = role;
    }
}
