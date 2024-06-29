package com.skhuthon.sweet_little_kitty.app.dto.friend.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FriendResponseDto {
    private Long friendId;
    private String friendName;
    private String email;
    private boolean areWeFriend;

    public FriendResponseDto(Long friendId, String friendName, String email, boolean areWeFriend) {
        this.friendId = friendId;
        this.friendName = friendName;
        this.email = email;
        this.areWeFriend = areWeFriend;
    }
}
