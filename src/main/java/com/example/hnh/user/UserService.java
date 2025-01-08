package com.example.hnh.user;

import com.example.hnh.global.error.errorcode.ErrorCode;
import com.example.hnh.global.error.exception.CustomException;
import com.example.hnh.global.util.AuthenticationScheme;
import com.example.hnh.global.util.JwtProvider;
import com.example.hnh.user.dto.AccountRequest;
import com.example.hnh.user.dto.JwtAuthResponse;
import com.example.hnh.user.dto.UserRequestDto;
import com.example.hnh.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Security::AccountService")
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


    //회원 가입
    public UserResponseDto signUp(UserRequestDto requestDto){

        User user = new User(requestDto);
        user.setPassword(bCryptPasswordEncoder.encode(requestDto.getPassword()));
        User saveUser = userRepository.save(user);

        return new UserResponseDto(saveUser);
    }

    //로그인
    public JwtAuthResponse login(AccountRequest accountRequest) {
        User user = this.userRepository.findByEmail(accountRequest.getEmail())
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        this.validatePassword(accountRequest.getPassword(), user.getPassword());

        // 사용자 인증 후 인증 객체를 저장
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        accountRequest.getEmail(),
                        accountRequest.getPassword())
        );
        // 토큰 생성
        String accessToken = this.jwtProvider.generateToken(authentication);
        log.info("토큰 생성: {}", accessToken);
        return new JwtAuthResponse(AuthenticationScheme.BEARER.getName(), accessToken);
    }

    //회원 조회
    public UserResponseDto getUser(Long userId , User loginUser){
        User findUser = userRepository.findByIdOrElseThrow(userId);
        log.info(loginUser.getName());
        return new UserResponseDto(findUser);
    }

    //비밀번호 변경
    public String modifyPassword(Long userId , String currentPassword , String newPassword){
        User user = this.userRepository.findByIdOrElseThrow(userId);
        this.validatePassword(currentPassword,user.getPassword());
        if (Objects.equals(currentPassword, newPassword)){
            throw new CustomException(ErrorCode.PASSWORD_UPDATE_ERROR);
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
        return "변경되었습니다.";
    }

    private void validatePassword(String rawPassword, String encodedPassword)
            throws IllegalArgumentException {
        boolean notValid = !this.bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
        if (notValid) {
            throw new CustomException(ErrorCode.PASSWORD_ERROR);
        }
    }
}
