package com.example.hnh.user;

import com.example.hnh.global.error.errorcode.ErrorCode;
import com.example.hnh.global.error.exception.CustomException;
import com.example.hnh.user.dto.AdminCreateRequestDto;
import com.example.hnh.user.dto.AdminResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 관리자 생성 로직
     *
     * @param adminCreateRequestDto 관리자 생성 정보
     * @return
     */
    @Transactional
    public AdminResponseDto createAdmin(AdminCreateRequestDto adminCreateRequestDto) {

        //이메일 중복 검증
        Optional<User> findUser = userRepository.findByEmail(adminCreateRequestDto.getEmail());

        if (findUser.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        //관리자 객체 생성, 저장
        User admin = adminCreateRequestDto.toEntity();
        admin.setPassword(bCryptPasswordEncoder.encode(adminCreateRequestDto.getPassword()));
        admin.updateAuth(UserRole.ADMIN);
        User savedAdmin = userRepository.save(admin);

        return new AdminResponseDto(savedAdmin);
    }
}
