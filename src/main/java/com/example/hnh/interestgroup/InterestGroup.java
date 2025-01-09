package com.example.hnh.interestgroup;

import com.example.hnh.global.BaseEntity;
import com.example.hnh.group.Group;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "interest_group")
public class InterestGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(nullable = false)
    private Long userId;

    public InterestGroup() {}

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
