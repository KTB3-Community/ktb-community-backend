package com.ktb.community.service;

import com.ktb.community.common.enums.Code;
import com.ktb.community.common.exception.GeneralException;
import com.ktb.community.domain.Token;
import com.ktb.community.domain.User;
import com.ktb.community.dto.LoginRequestDto;
import com.ktb.community.dto.LoginResponseDto;
import com.ktb.community.dto.TokenResponseDto;
import com.ktb.community.repository.TokenRepository;
import com.ktb.community.repository.UserRepository;
import com.ktb.community.util.password.PasswordUtils;
import com.ktb.community.util.user.UserAuthenticationUtils;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final ImageService imageService;
    private final UserAuthenticationUtils userAuthenticationUtils;

    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        // 회원 가입 단계에서 이메일 유일을 보장하므로 해당 필드로 식별
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(Code.USER_NOT_FOUND));
        Long userId = user.getId();

        // 토큰 생성 (access token & refresh token)
        TokenResponseDto tokens;
        if (email.equals(user.getEmail()) && PasswordUtils.checkPassword(password, user.getPassword())) {
            tokens = tokenService.createTokens(user, response);
        } else {
            throw new GeneralException(Code.USER_MISMATCH);
        }

        // presigned 프로필 이미지 url
        String profileImageUrl = imageService.generatePresignedUrlWithKey(user.getProfileImageKey(), Duration.ofHours(1));

        return LoginResponseDto.builder()
                .userId(userId)
                .nickname(user.getNickname())
                .profileImageUrl(profileImageUrl)
                .tokenResponseDto(tokens) // access token만 노출
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public void logout() {
        Long userId = userAuthenticationUtils.getCurrentUserId();
        Token token = tokenRepository.findByUserId(userId);
        // 토큰 제거
        tokenRepository.delete(token);
    }

}
