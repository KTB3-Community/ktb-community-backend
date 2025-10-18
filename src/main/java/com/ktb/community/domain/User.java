package com.ktb.community.domain;

import com.ktb.community.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class User {

    private long id;
    private String email;
    private String nickname;
    private String password;
    private String profileImageKey;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder(toBuilder = true)
    public User(long id, String email, String nickname, String password, String profileImageKey, Role role,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.profileImageKey = profileImageKey;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User withId(long newId) {
        return this.toBuilder()
                .id(newId)
                .build();
    }

    public static User createUser(String email, String nickname, String password, String profileImageKey, Role role) {
        LocalDateTime now = LocalDateTime.now();
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .profileImageKey(profileImageKey)
                .role(role)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    // email이 id 역할이라 변경 안되게 함
    public void updateProfileInfo(String nickname) {
        this.nickname = nickname;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePassword(String password) {
        this.password = password;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateProfileImage(String profileImageKey) {
        this.profileImageKey = profileImageKey;
        this.updatedAt = LocalDateTime.now();
    }

    public void deleteProfileImage() {
        this.profileImageKey = null;
    }

}
