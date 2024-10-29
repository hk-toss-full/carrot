package com.carrot.user.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApplicationResponse<T> {

    private final boolean success;
    private final String message;
    private final T data;

    public static <T> ApplicationResponse<T> success(T data) {
        return new ApplicationResponse<>(true, null, data);
    }

    public static <T> ApplicationResponse<T> fail(String message) {
        return new ApplicationResponse<>(false, message, null);
    }
}
