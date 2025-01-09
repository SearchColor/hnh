package com.example.hnh.user;

import com.example.hnh.global.error.errorcode.ErrorCode;
import com.example.hnh.global.error.exception.CustomException;
import com.example.hnh.group.Group;
import com.example.hnh.group.GroupRepository;
import com.example.hnh.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
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
     * 관리자 통계 로직
     *
     * @param startDate 조회 기간 시작 날짜
     * @param endDate   조회 기간 마지막 날짜
     * @param groupName 조회 그룹 이름
     * @return
     */
    public DashboardResponseDto findStats(String startDate, String endDate, String groupName) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return groupRepository.findStatsByName(start.atStartOfDay(), end.atStartOfDay(), groupName);
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

    /**
     * 그룹 리포트 로직
     *
     * @param reportGroupRequestDto 그룹 리포트 정보
     * @return
     */
    public ReportGroupResponseDto reportGroup(ReportGroupRequestDto reportGroupRequestDto) {

        //그룹 조회
        Group findGroup = groupRepository.findById(reportGroupRequestDto.getGroupId()).orElseThrow(
                () -> new CustomException(ErrorCode.GROUP_NOT_FOUND));

        //요청 값과 같은 값인지 검증
        if (Objects.equals(findGroup.getStatus(), reportGroupRequestDto.getStatus())) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        //그룹 상태 변경, 저장
        findGroup.setStatus(reportGroupRequestDto.getStatus());
        Group savedGroup = groupRepository.save(findGroup);

        return new ReportGroupResponseDto(savedGroup.getId(), savedGroup.getStatus());
    }
}
