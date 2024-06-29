package com.skhuthon.sweet_little_kitty.app.service.friend;

import com.skhuthon.sweet_little_kitty.app.dto.friend.response.FriendResponseDto;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FriendService {
    ApiResponseTemplate<Void> addFriend(String friendEmail, Authentication authentication);
    ApiResponseTemplate<List<FriendResponseDto>> listFriends(Authentication authentication);
    ApiResponseTemplate<Void> deleteFriend(Long id, Long userId);
}
