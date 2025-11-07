package com.ktb.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credential")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Credential extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;
    private String hashType;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    protected Credential(String password, String hashType, User user) {
        this.password = password;
        this.hashType = hashType;
        this.user = user;
    }

    public static Credential createdCredential(String password, String hashType) {
        return Credential.builder()
                .password(password)
                .hashType(hashType)
                .build();
    }
}
