package com.kiwi_ticketing.kiwi_ticketing.global.exception;

import lombok.Getter;

@Getter
public class ErrorWrapper<T> {
    private final T error;

    public ErrorWrapper(T error) { this.error = error; }
}
