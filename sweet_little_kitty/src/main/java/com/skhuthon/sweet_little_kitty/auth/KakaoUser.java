package com.skhuthon.sweet_little_kitty.auth;

public class KakaoUser {
    private Long id;
    private String nickname;
    private String email;

    public KakaoUser(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }
}
