package com.carrot.user.controller;

import com.carrot.user.controller.client.KakaoApiClient;
import com.carrot.user.domain.dto.KakaoLoginResponse;
import com.carrot.user.domain.dto.UserResponse;
import com.carrot.user.global.ApplicationResponse;
import com.carrot.user.jwt.JwtAuthenticationProvider;
import com.carrot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oauth/kakao")
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoApiClient kakaoApiClient;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApplicationResponse<KakaoLoginResponse>> loginKakao(@RequestParam("code") String authorizationCode) {
        KakaoLoginResponse kakaoLoginResponse = userService.login(authorizationCode);
        return ResponseEntity.ok(ApplicationResponse.success(kakaoLoginResponse));
    }


}
