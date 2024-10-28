package com.carrot.user.domain.dto;

import com.carrot.user.domain.entity.User;

import java.util.Date;

public record UserRequest(String nickname) {

    public User toEntity(Long userId, Long kakaoId, String randomId, Date createAt) {
        return User.builder()
                .userId(userId)
                .kakaoId(kakaoId)
                .randomId(randomId)
                .nickname(nickname)
                .createdAt(createAt)
                .build();
    }
}
