package com.carrot.item.exception;

import com.carrot.item.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ItemErrorCode implements ErrorCode {

    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "아이템을 찾을 수 없습니다."),
    ITEM_ALREADY_EXISTS(HttpStatus.CONFLICT, "아이템이 이미 존재합니다."),
    INVALID_ITEM(HttpStatus.BAD_REQUEST, "유효하지 않은 데이터입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
