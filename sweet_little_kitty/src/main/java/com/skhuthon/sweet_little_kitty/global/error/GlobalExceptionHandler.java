package com.skhuthon.sweet_little_kitty.global.error;

import com.skhuthon.sweet_little_kitty.global.error.dto.ErrorResponse;
import com.skhuthon.sweet_little_kitty.global.error.dto.SuccessResponse;
import com.skhuthon.sweet_little_kitty.global.error.enumType.SuccessCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatus(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getHttpStatus()));
    }

    public ResponseEntity<SuccessResponse> handleSuccessResponse(SuccessCode successCode) {
        SuccessResponse successResponse = new SuccessResponse(successCode.getHttpStatusCode(), successCode.getMessage());
        return new ResponseEntity<>(successResponse, successCode.getHttpStatus());
    }
}
