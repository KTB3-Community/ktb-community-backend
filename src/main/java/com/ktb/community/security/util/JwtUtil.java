package com.ktb.community.security.util;

import com.ktb.community.common.enums.Code;
import com.ktb.community.common.exception.GeneralException;
import com.ktb.community.domain.enums.Role;
import com.ktb.community.domain.enums.TokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.SignatureException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.SQLOutput;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${JWT_SECRET_KEY}")
    private String secretKey;
    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // Authentication 객체의 username으로 Access Token 발급
    public String createAccessToken(String email, Role role) {
        Date now = new Date();
        // 1시간
        long ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 1000L;
        Date expiry = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.name())
                .claim("type", TokenType.ACCESS.name())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Authentication 객체의 username으로 Refresh Token 발급
    public String createRefreshToken(String email) {
        Date now = new Date();
        // 7일
        long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 7;
        Date expiry = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setSubject(email)
                .claim("type", TokenType.REFRESH.name())          // refresh token 구분
                // 권한 정보 넣지 않음
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token); // 서명 + 만료 검증
            return true;
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            log.warn("[JWT Expired] {}", e.getMessage());
            throw new GeneralException(Code.TOKEN_EXPIRED);
        } catch (SignatureException e) {
            // 서명이 잘못된 경우
            log.warn("[JWT Signature Invalid] {}", e.getMessage());
            throw new GeneralException(Code.INVALID_TOKEN);
        } catch (Exception e) {
            // 기타 예외
            log.warn("[JWT Error] {}", e.getMessage());
            throw new GeneralException(Code.UNAUTHORIZED);
        }
    }

    // 공통적으로 Claims 파싱하는 메서드
    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // 만료된 토큰이어도 claims는 반환 가능
            return e.getClaims();
        }
    }

    // username 반환
    public String getUsername(String token) {
        try {
            return parseClaims(token).get("sub", String.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    // role 반환
    public Role getRole(String token) {
        try {
            String role = parseClaims(token).get("role", String.class);
            return Role.valueOf(role); // "USER" → Role.USER
        } catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }


}
