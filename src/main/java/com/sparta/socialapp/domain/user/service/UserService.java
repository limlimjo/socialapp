package com.sparta.socialapp.domain.user.service;

import com.sparta.socialapp.common.api.ApiResponse;
import com.sparta.socialapp.common.config.JwtUtil;
import com.sparta.socialapp.domain.user.dto.UserRequestDto;
import com.sparta.socialapp.domain.user.entity.User;
import com.sparta.socialapp.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입
    public ResponseEntity<ApiResponse<String>> register(UserRequestDto requestDto) {
        // 중복회원 체크
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            return ApiResponse.error(HttpStatus.CONFLICT, "EMAIL_DUPLICATE", "이미 사용중인 이메일입니다.");
        }

        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 유저 생성
        User user = User.builder()
                .email(requestDto.getEmail())
                .username(requestDto.getUsername())
                .passwordHash(encryptedPassword)
                .build();

        // 유저 저장
        userRepository.save(user);
        return ApiResponse.success("회원가입에 성공하였습니다.");
    }

    // 로그인
    public ResponseEntity<ApiResponse<String>> login(UserRequestDto requestDto) {
        // 사용자 조회
        Optional<User> userInfo = userRepository.findByEmail(requestDto.getEmail());

        if (userInfo.isEmpty()) {
            return ApiResponse.error(HttpStatus.UNAUTHORIZED, "EMAIL_NOT_FOUND", "가입되지 않은 이메일입니다.");
        }

        User user = userInfo.get();

        // 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPasswordHash())) {
            return ApiResponse.error(HttpStatus.UNAUTHORIZED, "PASSWORD_INVALID", "비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 발급
        String token = jwtUtil.generateToken(user.getEmail());

        return ApiResponse.success(token);
    }
}
