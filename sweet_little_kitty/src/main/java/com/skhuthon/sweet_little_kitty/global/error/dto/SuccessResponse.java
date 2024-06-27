package com.skhuthon.sweet_little_kitty.global.error.dto;

public record SuccessResponse(
        int statusCode,
        String message
) {
}
