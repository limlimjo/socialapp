package com.sparta.socialapp.domain.user.controller;

import com.sparta.socialapp.common.api.ApiResponse;
import com.sparta.socialapp.domain.user.dto.UserRequestDto;
import com.sparta.socialapp.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 회원가입 처리
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody UserRequestDto requestDto) {
        return userService.register(requestDto);
    }

    // 로그인 처리
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserRequestDto requestDto) {
        return userService.login(requestDto);
    }
}
