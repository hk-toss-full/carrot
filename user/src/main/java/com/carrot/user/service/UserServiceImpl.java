package com.carrot.user.service;

import com.carrot.user.controller.client.KakaoApiClient;
import com.carrot.user.domain.dto.*;
import com.carrot.user.domain.entity.User;
import com.carrot.user.exception.UserErrorCode;
import com.carrot.user.global.exception.ApplicationException;
import com.carrot.user.jwt.JwtAuthenticationProvider;
import com.carrot.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final KakaoApiClient kakaoApiClient;

    @Override
    public KakaoLoginResponse login(String authorizationCode) {
        KakaoTokenResponse kakaoTokenResponse = kakaoApiClient.requestAccessToken(authorizationCode);
        KakaoInfoResponse kakaoInfoResponse = kakaoApiClient.requestKakaoUserInfo(kakaoTokenResponse.accessToken());

        Long userId = findOrCreateMember(kakaoInfoResponse);
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        String accessToken = jwtAuthenticationProvider.createAccessToken(user.getUserId());

        return new KakaoLoginResponse(user.getUserId(), user.getRandomId(), accessToken);
    }

    @Override
    public UserResponse getUserInfo(Long userId) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        return new UserResponse(user.getNickname(), user.getRandomId());
    }

    @Override
    public UserResponse updateUserNickname(Long userId, UpdateNicknameRequest request) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        user.setNickname(request.nickname());
        userJpaRepository.save(user);
        return new UserResponse(user.getNickname(), user.getRandomId());
    }

    private Long findOrCreateMember(KakaoInfoResponse kakaoInfoResponse) {
        return userJpaRepository.findByKakaoId(kakaoInfoResponse.id())
                .map(User::getUserId)
                .orElseGet(() -> newMember(kakaoInfoResponse));
    }

    private Long newMember(KakaoInfoResponse kakaoInfoResponse) {
        String randomId = UUID.randomUUID().toString();
        User user = User.builder()
                .kakaoId(kakaoInfoResponse.id())
                .nickname(null)
                .randomId(randomId)
                .createdAt(new Date())
                .build();

        userJpaRepository.save(user);
        return user.getUserId();
    }
}
