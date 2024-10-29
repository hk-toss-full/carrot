package com.carrot.user.controller;

import com.carrot.user.domain.dto.KakaoLoginResponse;
import com.carrot.user.global.ApplicationResponse;
import com.carrot.user.jwt.JwtAuthenticationProvider;
import com.carrot.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/oauth/kakao")
@RequiredArgsConstructor
public class KakaoController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApplicationResponse<KakaoLoginResponse>> kakaoLogin(
            @RequestParam("code") String authorizationCode) {
        KakaoLoginResponse kakaoLoginResponse = userService.login(authorizationCode);
        return ResponseEntity.ok(ApplicationResponse.success(kakaoLoginResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> KakaoLogout(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verify-token")
    public ResponseEntity<ApplicationResponse<Long>> verifyToken(@RequestHeader("Authorization") String token) {
        String accessToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        Long userId = userService.verifyAccessToken(accessToken);
        return ResponseEntity.ok(ApplicationResponse.success(userId));
    }
}
