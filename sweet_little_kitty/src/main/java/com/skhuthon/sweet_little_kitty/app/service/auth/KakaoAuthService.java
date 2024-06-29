package com.skhuthon.sweet_little_kitty.app.service.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhuthon.sweet_little_kitty.app.entity.user.User;
import com.skhuthon.sweet_little_kitty.app.repository.user.UserRepository;
import com.skhuthon.sweet_little_kitty.global.exception.code.ErrorCode;
import com.skhuthon.sweet_little_kitty.global.exception.code.SuccessCode;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import com.skhuthon.sweet_little_kitty.auth.KakaoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class KakaoAuthService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Autowired
    private UserRepository userRepository;

    public ApiResponseTemplate<KakaoLoginResponseDto> kakaoLogin(String code) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String tokenUrl = "https://kauth.kakao.com/oauth/token";
            String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", clientId);
            params.add("redirect_uri", redirectUri);
            params.add("code", code);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<String> tokenResponse = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            if (tokenResponse.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("액세스 토큰을 가져오는데 실패했습니다.");
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode tokenJson = mapper.readTree(tokenResponse.getBody());
            String accessToken = tokenJson.get("access_token").asText();

            HttpHeaders userInfoHeaders = new HttpHeaders();
            userInfoHeaders.setBearerAuth(accessToken);

            HttpEntity<Void> userInfoRequest = new HttpEntity<>(userInfoHeaders);

            ResponseEntity<String> userInfoResponse = restTemplate.exchange(
                    userInfoUrl,
                    HttpMethod.GET,
                    userInfoRequest,
                    String.class
            );

            if (userInfoResponse.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("사용자 정보를 가져오는데 실패했습니다.");
            }

            JsonNode userJson = mapper.readTree(userInfoResponse.getBody());
            JsonNode kakaoAccount = userJson.get("kakao_account");

            Long id = userJson.get("id").asLong();
            String nickname = kakaoAccount.get("profile").get("nickname").asText();
            String email = kakaoAccount.get("email").asText();

            KakaoUser kakaoUser = new KakaoUser(id, nickname, email);
            saveOrUpdateUser(kakaoUser);

            KakaoLoginResponseDto responseDto = new KakaoLoginResponseDto(email);
            return ApiResponseTemplate.success(SuccessCode.LOGIN_USER_SUCCESS, responseDto);
        } catch (HttpClientErrorException.BadRequest badRequest) {
            String responseBody = badRequest.getResponseBodyAsString();
            return ApiResponseTemplate.error(ErrorCode.AUTHENTICATION_FAILED_EXCEPTION, responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponseTemplate.error(ErrorCode.VALIDATION_REQUEST_FAIL_USERINFO_EXCEPTION, "카카오 로그인 실패");
        }
    }

    private void saveOrUpdateUser(KakaoUser kakaoUser) {
        Optional<User> userOptional = userRepository.findByEmail(kakaoUser.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.update(kakaoUser.getNickname());
        } else {
            user = User.builder()
                    .name(kakaoUser.getNickname())
                    .email(kakaoUser.getEmail())
                    .build();
        }

        userRepository.save(user);
    }
}
