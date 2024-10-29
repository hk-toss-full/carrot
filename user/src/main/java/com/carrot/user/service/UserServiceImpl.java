package com.carrot.user.service;

import com.carrot.user.controller.client.KakaoApiClient;
import com.carrot.user.domain.dto.*;
import com.carrot.user.domain.entity.User;
import com.carrot.user.exception.UserErrorCode;
import com.carrot.user.global.exception.ApplicationException;
import com.carrot.user.jwt.JwtAuthenticationProvider;
import com.carrot.user.repository.UserJpaRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, CustomUserDetailsService {

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
        String refreshToken = jwtAuthenticationProvider.createRefreshToken(user.getUserId());

        return new KakaoLoginResponse(user.getUserId(), user.getRandomId(), accessToken);
    }

    @Override
    public UserResponse getUserInfo(Long userId) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        return UserResponse.from(user);
    }

    @Override
    public UserResponse updateUserNickname(Long userId, UserRequest request) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));

        User updatedUser = request.toEntity(userId, user.getKakaoId(), user.getRandomId(), user.getCreatedAt());
        userJpaRepository.save(updatedUser);
        return UserResponse.from(updatedUser);
    }

    @Override
    public void logout(HttpServletRequest request) {
        String accessToken = jwtAuthenticationProvider.resolveAccessToken(request);
        if (accessToken == null) {
            throw new ApplicationException(UserErrorCode.ACCESS_TOKEN_NULL);
        }
        kakaoApiClient.logout(accessToken);
    }

    @Override
    public Long verifyAccessToken(String accessToken) {
        if (!jwtAuthenticationProvider.validateToken(accessToken)) {
            throw new ApplicationException(UserErrorCode.INVALID_TOKEN);
        }
        Claims claims = jwtAuthenticationProvider.getClaims(accessToken);
        return claims.get("userId", Long.class);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("Username not used for authentication");
    }

    @Override
    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        return new UserDetailsImpl(user.getUserId(), user.getKakaoId(), user.getNickname(), null);
    }
}
