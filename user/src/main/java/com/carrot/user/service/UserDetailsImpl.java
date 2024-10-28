package com.carrot.user.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final Long userId;
    private final Long kakaoId;
    private final String nickname;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long userId, Long kakaoId, String nickname,
                           Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getKakaoId() {
        return kakaoId;
    }

    public String getNickname() {
        return nickname;
    }
}
