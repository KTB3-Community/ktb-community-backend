package com.ktb.community.security.config;

import com.ktb.community.security.CustomUserDetails;
import com.ktb.community.security.util.JwtUtil;
import com.ktb.community.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
// Bean은 Spring Container에 존재 (@Component로 Bean이니까)
// 실행은 Servlet Container Filter Chain에서 수행
// JWT 기반 인증의 핵심 역할
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. 헤더에서 토큰 추출
        String token = resolveToken(request);

        // 2. 토큰 유효성 검사
        if (token != null && jwtUtil.validateToken(token)) {
            String email = jwtUtil.getUsername(token);

            // customUserDetailsService의 loadByUsername을 통해 CustomUserDetails 객체 반환
            CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(email);
            // 인증된 객체 생성 (authenticated = true)
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    customUserDetails, // Principal, 인증된 사용자 정보
                    null, // Credential, 비밀번호
                    customUserDetails.getAuthorities() // Authorities, 권한 목록
            );
            // 인증된 인증 객체를 SecurityContextHolder에 담음
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
