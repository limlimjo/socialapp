package com.sparta.socialapp.common.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

// JWT 기반 인증을 처리하는 Spring Security 필터
// 클라이언트가 요청을 보낼 때 Authorization 헤더에 포함된 JWT를 검증하여 사용자를 인증하는 역할을 함
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            String email = jwtUtil.validateToken(jwt);

            if (email != null) {
                SecurityContextHolder.getContext().setAuthentication(
                        new PreAuthenticatedAuthenticationToken(email, null, null)
                );
            }
        }
        chain.doFilter(request, response);
    }
}
