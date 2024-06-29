package com.skhuthon.sweet_little_kitty.global.exception.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    // 200 OK
    LOGIN_USER_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    GET_DIARY_SUCCESS(HttpStatus.OK, "일기 조회가 완료되었습니다."),
    GET_FRIENDS_SUCCESS(HttpStatus.OK, "친구 목록 조회가 완료되었습니다."),
    GET_ALL_DIARIES_SUCCESS(HttpStatus.OK, "사용자가 작성한 모든 일기를 조회했습니다."),
    ADD_FRIEND_SUCCESS(HttpStatus.OK, "친구 추가가 완료되었습니다."),
    DELETE_FRIEND_SUCCESS(HttpStatus.OK, "친구 삭제가 완료되었습니다."),

    // 201 Created
    CREATE_DIARY_SUCCESS(HttpStatus.CREATED, "일기 생성이 완료되었습니다."),

    // 204 No Content
    DELETE_DIARY_SUCCESS(HttpStatus.NO_CONTENT, "일기 삭제가 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
