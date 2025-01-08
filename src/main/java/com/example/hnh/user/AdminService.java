package com.example.hnh.user;

import com.example.hnh.global.error.errorcode.ErrorCode;
import com.example.hnh.global.error.exception.CustomException;
import com.example.hnh.user.dto.AdminCreateRequestDto;
import com.example.hnh.user.dto.AdminResponseDto;
import com.example.hnh.user.dto.ReportUserRequestDto;
import com.example.hnh.user.dto.ReportUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
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

    /**
     * 유저 리포트 로직
     *
     * @param reportUserRequestDto 유저 리포트 정보
     * @return
     */
    @Transactional
    public ReportUserResponseDto reportUser(ReportUserRequestDto reportUserRequestDto) {

        //유저 조회
        User findUser = userRepository.findById(reportUserRequestDto.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //요청 값과 같은 값인지 검증
        if (Objects.equals(findUser.getStatus(), reportUserRequestDto.getStatus())) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        //유저 상태 변경, 저장
        findUser.setStatus(reportUserRequestDto.getStatus());
        User savedUser = userRepository.save(findUser);

        return new ReportUserResponseDto(savedUser.getId(), savedUser.getStatus());
    }
}
