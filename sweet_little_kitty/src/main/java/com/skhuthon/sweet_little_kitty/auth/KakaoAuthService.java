package com.skhuthon.sweet_little_kitty.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoAuthService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    public KakaoUser kakaoLogin(String code) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String tokenUrl = "https://kauth.kakao.com/oauth/token";
            String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            Map<String, String> params = new HashMap<>();
            params.put("grant_type", "authorization_code");
            params.put("client_id", clientId);
            params.put("redirect_uri", redirectUri);
            params.put("code", code);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<String> tokenResponse = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    request,
                    String.class
            );

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

            JsonNode userJson = mapper.readTree(userInfoResponse.getBody());
            JsonNode kakaoAccount = userJson.get("kakao_account");

            Long id = userJson.get("id").asLong();
            String nickname = kakaoAccount.get("profile").get("nickname").asText();
            String email = kakaoAccount.get("email").asText();

            return new KakaoUser(id, nickname, email);
        } catch (Exception e) {
            throw new RuntimeException("카카오 로그인 실패", e);
        }
    }
}
