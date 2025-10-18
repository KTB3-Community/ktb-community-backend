package com.ktb.community.service;

import com.ktb.community.common.enums.Code;
import com.ktb.community.common.exception.GeneralException;
import com.ktb.community.dto.TokenRefreshRequestDto;
import com.ktb.community.dto.TokenRefreshResponseDto;
import com.ktb.community.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final TokenRepository tokenRepository;

    public String createAccessToken(Long userId) {
        Date now = new Date();
        // 15분
        long accessTokenValidity = 1000 * 60 * 15;
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidity))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(Long userId) {
        Date now = new Date();
        // 7일
        long refreshTokenValidity = 1000L * 60 * 60 * 24 * 7;
        String refreshToken = Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidity))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        tokenRepository.save(userId, refreshToken);
        return refreshToken;
    }

    public TokenRefreshResponseDto refreshTokens(TokenRefreshRequestDto tokenRefreshRequestDto) {
        String refreshToken = tokenRefreshRequestDto.getRefreshToken();

        Claims claims = parseToken(refreshToken);
        Long userId = Long.parseLong(claims.getSubject());

        String savedRefreshToken = tokenRepository.find(userId);
        if (!refreshToken.equals(savedRefreshToken)) {
            throw new GeneralException(Code.INVALID_TOKEN);
        }

        String newAccessToken = createAccessToken(userId);
        String newRefreshToken = createRefreshToken(userId);

        tokenRepository.delete(userId);

        return TokenRefreshResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public Claims parseToken(String refreshToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Long extractUserId(String token) {
        Claims claims = parseToken(token);
        return Long.parseLong(claims.getSubject());
    }



}
