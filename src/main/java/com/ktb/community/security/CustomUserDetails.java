package com.ktb.community.security;

import com.ktb.community.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    // role 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    // password 반환
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // username 반환
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; // 임의로 만료되지 않았다고 설정
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true; // 임의로 잠금되지 않았다고 설정
    }

    // 비밀번호 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 임의로 만료되지 않았다고 설정
    }

    // 계정 활성화 여부 반환
    @Override
    public boolean isEnabled() {
        return true; // 임의로 활성화 되었다고 설정
    }
}
