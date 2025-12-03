package com.ktb.community.service;

import com.ktb.community.common.enums.Code;
import com.ktb.community.common.exception.GeneralException;
import com.ktb.community.domain.Token;
import com.ktb.community.domain.User;
import com.ktb.community.domain.enums.Role;
import com.ktb.community.dto.TokenRefreshRequestDto;
import com.ktb.community.dto.TokenRefreshResponseDto;
import com.ktb.community.dto.TokenResponseDto;
import com.ktb.community.mapper.TokenMapper;
import com.ktb.community.repository.TokenRepository;
import com.ktb.community.repository.UserRepository;
import com.ktb.community.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final JwtUtil jwtUtil;
    private final TokenMapper tokenMapper;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public TokenResponseDto createTokens(User user, HttpServletResponse response) {
        String email = user.getEmail();
        Role role = user.getRole();
        String accessToken = jwtUtil.createAccessToken(email, role);
        String refreshToken = jwtUtil.createRefreshToken(email);
        addRefreshTokenCookie(response, refreshToken);

        tokenRepository.save(
                Token.createToken(user, accessToken, refreshToken)
        );

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }

    public TokenRefreshResponseDto refreshAccessTokenAndRefreshToken(TokenRefreshRequestDto tokenRefreshRequestDto) {
        String refreshToken = tokenRefreshRequestDto.getRefreshToken();

        Claims claims = jwtUtil.parseClaims(refreshToken);

        String email = claims.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(Code.USER_NOT_FOUND));

        Token savedToken = tokenRepository.findByUserId(user.getId());

        if (!refreshToken.equals(savedToken.getRefreshToken())) {
            throw new GeneralException(Code.INVALID_TOKEN);
        }

        Role role = user.getRole();

        String newAccessToken = jwtUtil.createAccessToken(email, role);
        jwtUtil.createRefreshToken(email);

        tokenRepository.delete(savedToken);

        return tokenMapper.mapToTokenRefreshResponseDto(newAccessToken);
    }

    public void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")   // 또는 Lax
                .path("/")            // 전체 경로에서 쿠키 전송
                .maxAge(60 * 60 * 24 * 7) // 7일
                .build();

        response.setHeader("Set-Cookie", cookie.toString());
    }


}
