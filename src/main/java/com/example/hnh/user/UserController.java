package com.example.hnh.user;

import com.example.hnh.user.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userDto) {
        return new ResponseEntity<>(userService.signUp(userDto), HttpStatus.CREATED);
    }

    // 사용자 로그인
    @PostMapping("/login")
    public ResponseEntity<CommonResponseBody<JwtAuthResponse>> login(
            @Valid @RequestBody AccountRequest accountRequest) {
        JwtAuthResponse authResponse = this.userService.login(accountRequest);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(authResponse.getTokenAuthScheme(),
                authResponse.getAccessToken());

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(new CommonResponseBody<>("로그인 성공"));
    }
}
