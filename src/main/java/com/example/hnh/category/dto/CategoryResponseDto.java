package com.example.hnh.category.dto;

import com.example.hnh.category.Category;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CategoryResponseDto {

    private final Long categoryId;

    private final String name;

    private final LocalDateTime createdAt;

    public CategoryResponseDto(Long categoryId, String name, LocalDateTime createdAt) {
        this.categoryId = categoryId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public CategoryResponseDto(Category category) {
        this.categoryId = category.getId();
        this.name = category.getName();
        this.createdAt = category.getCreatedAt();
    }
}
