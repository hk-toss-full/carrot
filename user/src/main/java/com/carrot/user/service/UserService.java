package com.carrot.user.service;

import com.carrot.user.domain.dto.KakaoLoginResponse;
import com.carrot.user.domain.dto.UpdateNicknameRequest;
import com.carrot.user.domain.dto.UserResponse;

public interface UserService {

    KakaoLoginResponse login(String authorizationCode);
    UserResponse getUserInfo(Long userId);
    UserResponse updateUserNickname(Long userId, UpdateNicknameRequest request);
}
