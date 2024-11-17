package com.example.standard1.domain.user.controller;

import com.example.standard1.domain.user.dto.request.CreateAccessTokenRequest;
import com.example.standard1.domain.user.dto.response.CreateAccessTokenResponse;
import com.example.standard1.domain.user.service.RefreshTokenService;
import com.example.standard1.domain.user.service.TokenService;
import com.example.standard1.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/api/token")
    public ResponseEntity<ApiResponse<CreateAccessTokenResponse>> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(new CreateAccessTokenResponse(newAccessToken)));
    }

    @DeleteMapping("/api/refresh-token")
    public ResponseEntity deleteRefreshToken() {
        refreshTokenService.delete();

        return ResponseEntity.ok()
                .build();
    }
}