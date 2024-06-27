package com.skhuthon.sweet_little_kitty.global.error.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {
}
