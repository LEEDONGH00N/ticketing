package com.kiwi_ticketing.kiwi_ticketing.global.response;

public class DataWrapper<T> {
    private T data;

    public DataWrapper(T data) { this.data = data; }
}
