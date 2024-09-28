package com.kiwi_ticketing.kiwi_ticketing.global.exception.model;

import lombok.Getter;

@Getter
public class SimpErrorResponse {
    private final int code;
    private final String message;

    public SimpErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
