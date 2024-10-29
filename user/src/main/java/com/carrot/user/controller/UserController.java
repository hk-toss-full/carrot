package com.carrot.user.controller;

import com.carrot.user.domain.dto.UserRequest;
import com.carrot.user.domain.dto.UserResponse;
import com.carrot.user.global.ApplicationResponse;
import com.carrot.user.jwt.JwtAuthenticationProvider;
import com.carrot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @GetMapping
    public ResponseEntity<ApplicationResponse<UserResponse>> getUserInfo() {
        long userId = jwtAuthenticationProvider.getUserId();
        UserResponse userInfo = userService.getUserInfo(userId);
        return ResponseEntity.ok(ApplicationResponse.success(userInfo));
    }

    @PutMapping
    public ResponseEntity<ApplicationResponse<UserResponse>> updateUserNickname(@RequestBody UserRequest request) {
        long userId = jwtAuthenticationProvider.getUserId();
        UserResponse updateUserInfo = userService.updateUserNickname(userId, request);
        return ResponseEntity.ok(ApplicationResponse.success(updateUserInfo));
    }

}
