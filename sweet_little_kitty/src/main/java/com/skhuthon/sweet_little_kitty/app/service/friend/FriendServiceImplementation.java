package com.skhuthon.sweet_little_kitty.app.service.friend;

import com.skhuthon.sweet_little_kitty.app.dto.friend.request.FriendRequestDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendServiceImplementation implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public ApiResponseTemplate<FriendResponseDto> addFriend(FriendRequestDto friendRequestDto, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION, "해당 사용자를 찾을 수 없습니다" + userId));

        User friendUser = userRepository.findByEmail(friendRequestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION, "해당 사용자를 찾을 수 없습니다" + friendRequestDto.getEmail()));

        if (friendRepository.existsByUserAndFriendName(user, friendUser.getName())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_USER_EXCEPTION, "이미 친구인 사용자입니다.");
        }

        Friend friend = Friend.builder()
                .friendName(friendUser.getName())
                .areWeFriend(true)
                .user(user)
                .build();

        friendRepository.save(friend);

        FriendResponseDto resDto = FriendResponseDto.builder()
                .friendId(friend.getFriendId())
                .friendName(friend.getFriendName())
                .email(friendUser.getEmail())
                .areWeFriend(friend.isAreWeFriend())
                .build();

        return ApiResponseTemplate.<FriendResponseDto>builder()
                .status(200)
                .success(true)
                .message("친구 추가 성공")
                .data(resDto)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseTemplate<List<FriendResponseDto>> listFriends(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION, "User not found"));

        List<Friend> friends = friendRepository.findByUserUserId(userId);
        List<FriendResponseDto> friendResponseDtos = friends.stream()
                .map(friend -> new FriendResponseDto(friend.getFriendId(), friend.getFriendName(), friend.getUser().getEmail(), friend.isAreWeFriend()))
                .collect(Collectors.toList());
        return ApiResponseTemplate.success(SuccessCode.GET_DIARY_SUCCESS, friendResponseDtos);
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
