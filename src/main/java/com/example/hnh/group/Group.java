package com.example.hnh.group;

import com.example.hnh.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "`group`")
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private String name;

    @Column(name = "imagepath")
    private String imagePath;

    @Column(nullable = false)
    private String detail;


    public Group() {};

    public Group(Long userId, Long categoryId, String name, String imagePath, String detail) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.name = name;
        this.imagePath = imagePath;
        this.detail = detail;
    }
}
