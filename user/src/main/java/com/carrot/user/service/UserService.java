package com.carrot.user.service;

import com.carrot.user.domain.dto.KakaoLoginResponse;
import com.carrot.user.domain.dto.UserRequest;
import com.carrot.user.domain.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    KakaoLoginResponse login(String authorizationCode);
    UserResponse getUserInfo(Long userId);
    UserResponse updateUserNickname(Long userId, UserRequest request);
    void logout(HttpServletRequest request);
}
