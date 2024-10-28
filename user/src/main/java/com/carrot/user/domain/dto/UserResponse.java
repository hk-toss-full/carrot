package com.carrot.user.domain.dto;

import com.carrot.user.domain.entity.User;

public record UserResponse(String nickname, String userRandomId) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getNickname(), user.getRandomId());
    }
}
