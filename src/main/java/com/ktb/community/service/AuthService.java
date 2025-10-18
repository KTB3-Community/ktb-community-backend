package com.ktb.community.service;

import com.ktb.community.common.enums.Code;
import com.ktb.community.common.exception.GeneralException;
import com.ktb.community.domain.User;
import com.ktb.community.dto.LoginRequestDto;
import com.ktb.community.dto.LoginResponseDto;
import com.ktb.community.repository.TokenRepository;
import com.ktb.community.repository.UserRepository;
import com.ktb.community.util.password.PasswordUtils;
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

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        // 회원 가입 단계에서 이메일 유일을 보장하므로 해당 필드로 식별 (회원 가입 to do 참고)
        User user = userRepository.findByEmail(email);
        Long userId = user.getId();

        String accessToken;
        String refreshToken;

        if (email.equals(user.getEmail()) && PasswordUtils.checkPassword(password, user.getPassword())) {
            accessToken = tokenService.createAccessToken(userId);
            refreshToken = tokenService.createRefreshToken(userId);
            tokenRepository.save(userId, refreshToken);
        } else {
            throw new GeneralException(Code.USER_MISMATCH);
        }

        String profileImageUrl = imageService.generatePresignedUrlWithKey(user.getProfileImageKey(), Duration.ofHours(1));

        return LoginResponseDto.builder()
                .userId(userId)
                .nickname(user.getNickname())
                .profileImageUrl(profileImageUrl)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public void logout(Long userId) {
        tokenRepository.delete(userId);
    }

}
