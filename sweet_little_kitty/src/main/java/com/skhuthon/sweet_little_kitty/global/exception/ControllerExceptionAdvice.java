package com.skhuthon.sweet_little_kitty.global.exception;

import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import com.skhuthon.sweet_little_kitty.global.exception.code.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResponseTemplate<String>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ApiResponseTemplate.error(e.getErrorCode(), e.getMessage()));
    }

    public <T> ResponseEntity<ApiResponseTemplate<T>> handleSuccess(SuccessCode successCode, T data) {
        return ResponseEntity.status(successCode.getHttpStatus())
                .body(ApiResponseTemplate.success(successCode, data));
    }
}
