package com.skhuthon.sweet_little_kitty.app.service.user;

import com.skhuthon.sweet_little_kitty.app.dto.user.response.UserInfoResDto;
import com.skhuthon.sweet_little_kitty.app.entity.user.User;
import com.skhuthon.sweet_little_kitty.app.repository.user.UserRepository;
import com.skhuthon.sweet_little_kitty.global.exception.CustomException;
import com.skhuthon.sweet_little_kitty.global.exception.code.ErrorCode;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ApiResponseTemplate<UserInfoResDto> getUserInfo(Principal principal) {
        Long userId = Long.parseLong(principal.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "사용자 : " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        UserInfoResDto resDto = UserInfoResDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();

        return ApiResponseTemplate.<UserInfoResDto>builder()
                .status(200)
                .success(true)
                .message("사용자 정보 조회 성공")
                .data(resDto)
                .build();
    }
}
