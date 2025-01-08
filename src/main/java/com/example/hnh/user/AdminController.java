package com.example.hnh.user;

import com.example.hnh.category.CategoryService;
import com.example.hnh.category.dto.CategoryResponseDto;
import com.example.hnh.category.dto.CreateCategoryRequestDto;
import com.example.hnh.user.dto.AdminCreateRequestDto;
import com.example.hnh.user.dto.AdminResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;
    private final CategoryService categoryService;

    /**
     * 관리자 생성 메서드
     *
     * @param adminCreateRequestDto 관리자 생성 정보
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<AdminResponseDto> createAdmin(
            @Valid @RequestBody AdminCreateRequestDto adminCreateRequestDto
    ) {

        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAdmin(adminCreateRequestDto));
    }

    /**
     * 카테고리 생성 메서드
     *
     * @param createCategoryRequestDto 카테고리 생성 정보
     * @return
     */
    @PostMapping("/categories")
    @Secured("Auth_ADMIN")
    public ResponseEntity<CategoryResponseDto> createCategory(
            @Valid @RequestBody CreateCategoryRequestDto createCategoryRequestDto
    ) {

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(createCategoryRequestDto));
    }
}
