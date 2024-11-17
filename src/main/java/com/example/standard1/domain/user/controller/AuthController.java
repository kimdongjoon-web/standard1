package com.example.standard1.domain.user.controller;

import com.example.standard1.domain.user.dto.request.UserLoginRequest;
import com.example.standard1.domain.user.dto.response.CreateAccessTokenResponse;
import com.example.standard1.domain.user.dto.response.TokenResponse;
import com.example.standard1.domain.user.entity.RefreshToken;
import com.example.standard1.domain.user.entity.User;
import com.example.standard1.domain.user.repository.RefreshTokenRepository;
import com.example.standard1.domain.user.service.UserService;
import com.example.standard1.global.config.jwt.TokenProvider;
import com.example.standard1.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody UserLoginRequest request) {
        // 1. Login ID/PW를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        User user = (User) authentication.getPrincipal();

        String accessToken = tokenProvider.generateToken(user, Duration.ofHours(2));
        String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(14));

        // 4. RefreshToken 저장
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByUserId(user.getId())
                .map(entity -> entity.update(refreshToken))
                .orElse(new RefreshToken(user.getId(), refreshToken));

        refreshTokenRepository.save(refreshTokenEntity);

        // 5. 토큰 발급
        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);

        return ResponseEntity.ok(ApiResponse.success(tokenResponse));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<CreateAccessTokenResponse>> refresh(@RequestHeader("Authorization") String refreshToken) {
        // Bearer prefix 처리
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7); // "Bearer " 길이
        }

        // 1. Refresh Token 검증
        if (!tokenProvider.validToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 2. Access Token 재발급
        Long id = tokenProvider.getUserId(refreshToken);
        User user = userService.findById(id);
        String newAccessToken = tokenProvider.generateToken(user, Duration.ofHours(2));
        return ResponseEntity.ok(ApiResponse.success(new CreateAccessTokenResponse(newAccessToken)));
    }

    // OAuth2 로그인은 OAuth2SuccessHandler에서 처리되므로 별도의 엔드포인트가 필요하지 않습니다.
}