package com.skhuthon.sweet_little_kitty.app.dto.user.response;

import lombok.Builder;

@Builder
public record UserInfoResDto(
        String name,
        String email
) {
}
