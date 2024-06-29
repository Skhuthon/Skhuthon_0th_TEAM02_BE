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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendServiceImplementation implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponseTemplate<Void> addFriend(FriendRequestDto friendRequestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION, "User not found"));
        User friendUser = userRepository.findByEmail(friendRequestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION, "Friend not found"));

        if (friendRepository.existsByUserAndFriendName(user, friendUser.getName())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_USER_EXCEPTION, "Friend already exists");
        }

        Friend friend = Friend.builder()
                .friendName(friendUser.getName())
                .areWeFriend(true)
                .user(user)
                .build();
        friendRepository.save(friend);
        return ApiResponseTemplate.success(SuccessCode.ADD_FRIEND_SUCCESS, null);
    }

    @Override
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
    public ApiResponseTemplate<Void> deleteFriend(Long id, Long userId) {
        if (!friendRepository.existsById(id)) {
            throw new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION, "Friend not found");
        }

        friendRepository.deleteByFriendIdAndUserUserId(id, userId);
        return ApiResponseTemplate.success(SuccessCode.DELETE_FRIEND_SUCCESS, null);
    }
}
