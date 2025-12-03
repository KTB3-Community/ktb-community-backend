package com.ktb.community.domain;

import com.ktb.community.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String nickname;
    private String password;
    private String profileImageKey;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime deletedAt;
    private boolean isDeleted;

    @Builder(toBuilder = true)
    protected User(String email, String nickname, String password, String profileImageKey, Role role,
                LocalDateTime deletedAt, boolean isDeleted) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.profileImageKey = profileImageKey;
        this.role = role;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
    }

//    public User withId(Long newId) {
//        return this.toBuilder()
//                .id(newId)
//                .build();
//    }

    public static User createUser(String email, String nickname, String password, String profileImageKey, Role role) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .profileImageKey(profileImageKey)
                .role(role)
                .deletedAt(null)
                .isDeleted(false)
                .build();
    }

    // email이 id 역할이라 변경 안되게 함
    public void updateProfileInfo(String nickname, String profileImageKey, String password) {
        this.nickname = nickname;
        this.profileImageKey = profileImageKey;
        this.password = password;
    }

}
