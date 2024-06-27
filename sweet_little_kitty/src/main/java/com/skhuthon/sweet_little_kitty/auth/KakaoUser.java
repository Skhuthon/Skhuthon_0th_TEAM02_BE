package com.skhuthon.sweet_little_kitty.auth;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class KakaoUser {

    @Id
    @GeneratedValue
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
