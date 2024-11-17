package com.example.standard1.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // User
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "Email already exists"),
    PHONE_ALREADY_EXISTS(HttpStatus.CONFLICT, "Phone already exists"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access denied"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "Invalid input");

    private final HttpStatus httpStatus;
    private final String message;
}
