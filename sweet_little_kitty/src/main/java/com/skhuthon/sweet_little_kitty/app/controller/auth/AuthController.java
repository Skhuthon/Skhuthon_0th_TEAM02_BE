package com.skhuthon.sweet_little_kitty.app.controller.auth;

import com.skhuthon.sweet_little_kitty.app.service.auth.KakaoAuthService;
import com.skhuthon.sweet_little_kitty.app.service.auth.KakaoLoginResponseDto;
import com.skhuthon.sweet_little_kitty.global.exception.code.SuccessCode;
import com.skhuthon.sweet_little_kitty.global.security.JwtTokenProvider;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "카카오 로그인", description = "카카오 로그인을 담당하는 api 그룹")
public class AuthController {

    private final KakaoAuthService kakaoAuthService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/kakao/callback")
    @Operation(summary = "카카오 로그인 콜백", description = "카카오 OAuth 인증 후 콜백으로 받는 endpoint", responses = {
            @ApiResponse(responseCode = "200", description = "카카오 로그인 성공"),
            @ApiResponse(responseCode = "500", description = "카카오 로그인 실패")
    })
    public ResponseEntity<ApiResponseTemplate<String>> kakaoCallback(@RequestParam String code) {
        ApiResponseTemplate<KakaoLoginResponseDto> data = kakaoAuthService.kakaoLogin(code);
        if (data.isSuccess()) {
            String email = data.getData().getEmail();
            String jwtToken = jwtTokenProvider.generateToken(email);
            return ResponseEntity.ok(ApiResponseTemplate.success(SuccessCode.LOGIN_USER_SUCCESS, jwtToken));
        } else {
            return ResponseEntity.status(data.getStatus()).body(ApiResponseTemplate.error(data.getErrorCode(), data.getMessage()));
        }
    }
}
