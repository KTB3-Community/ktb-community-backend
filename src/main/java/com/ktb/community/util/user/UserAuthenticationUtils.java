package com.ktb.community.util.user;

import com.ktb.community.common.enums.Code;
import com.ktb.community.common.exception.GeneralException;
import com.ktb.community.domain.User;
import com.ktb.community.domain.enums.Role;
import com.ktb.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthenticationUtils {

    private final UserRepository userRepository;

    // 현재 인증된 사용자 Member 객체 반환
    public User getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 인증 객체가 없거나 anonymousUser면 401
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new GeneralException(Code.UNAUTHORIZED);
        }

        // 이메일 추출
        String email = auth.getName();

        // DB에 해당 유저가 없으면 404
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(Code.USER_NOT_FOUND));
    }

    // 현재 인증된 사용자 ID를 반환
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    // 현재 인증된 사용자 Role을 반환
    public Role getCurrentUserRole() {
        return getCurrentUser().getRole();
    }
}
