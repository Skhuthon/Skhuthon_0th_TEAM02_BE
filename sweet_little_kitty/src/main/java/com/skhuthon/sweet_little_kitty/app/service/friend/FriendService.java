package com.skhuthon.sweet_little_kitty.app.service.friend;

import com.skhuthon.sweet_little_kitty.app.dto.friend.request.FriendRequestDto;
import com.skhuthon.sweet_little_kitty.app.dto.friend.response.FriendResponseDto;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;

import java.util.List;

public interface FriendService {
    ApiResponseTemplate<Void> addFriend(FriendRequestDto friendRequestDto, Long userId);
    ApiResponseTemplate<List<FriendResponseDto>> listFriends(Long userId);
    ApiResponseTemplate<Void> deleteFriend(Long id, Long userId);
}
