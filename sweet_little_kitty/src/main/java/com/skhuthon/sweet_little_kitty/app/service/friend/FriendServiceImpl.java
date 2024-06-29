package com.skhuthon.sweet_little_kitty.app.service.friend;

import com.skhuthon.sweet_little_kitty.app.dto.friend.response.FriendResponseDto;
import com.skhuthon.sweet_little_kitty.app.entity.friend.Friend;
import com.skhuthon.sweet_little_kitty.app.entity.user.User;
import com.skhuthon.sweet_little_kitty.app.repository.friend.FriendRepository;
import com.skhuthon.sweet_little_kitty.app.repository.user.UserRepository;
import com.skhuthon.sweet_little_kitty.global.exception.CustomException;
import com.skhuthon.sweet_little_kitty.global.exception.code.ErrorCode;
import com.skhuthon.sweet_little_kitty.global.exception.code.SuccessCode;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public ApiResponseTemplate<Void> addFriend(String friendEmail, Authentication authentication) {

        User friendUser = userRepository.findByEmail(friendEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION, "해당 사용자를 찾을 수 없습니다"));

        if (friendRepository.existsByUserAndFriendName(authentication, friendUser.getName())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_USER_EXCEPTION, "이미 친구인 사용자입니다.");
        }

        Friend friend = Friend.builder()
                .friendName(friendUser.getName())
                .areWeFriend(true)
                .build();

        friendRepository.save(friend);

        return ApiResponseTemplate.<Void>builder()
                .status(201)
                .success(true)
                .message("친구 추가 성공")
                .build();
    }

    @Transactional(readOnly = true)
    public ApiResponseTemplate<List<FriendResponseDto>> listFriends(Authentication authentication) {
        String userEmail = authentication.getName();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION, "인증된 사용자를 찾을 수 없습니다."));

        List<Friend> friends = friendRepository.findByUser(user);
        List<FriendResponseDto> friendResponseDtos = friends.stream()
                .map(friend -> new FriendResponseDto(friend.getFriendId(), friend.getFriendName(), friend.getUser().getEmail(), friend.isAreWeFriend()))
                .collect(Collectors.toList());
        return ApiResponseTemplate.success(SuccessCode.GET_FRIENDS_SUCCESS, friendResponseDtos);
    }

    @Override
    @Transactional
    public ApiResponseTemplate<Void> deleteFriend(Long id, Long userId) {
        if (!friendRepository.existsById(id)) {
            throw new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION, "Friend not found");
        }

        friendRepository.deleteByFriendIdAndUserUserId(id, userId);
        return ApiResponseTemplate.success(SuccessCode.DELETE_FRIEND_SUCCESS, null);
    }
}
