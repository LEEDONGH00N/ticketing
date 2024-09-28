package com.kiwi_ticketing.kiwi_ticketing.global.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonException extends RuntimeException{
    private CommonResponseStatus error;

    public CommonException(CommonResponseStatus error) {
        super(error.getMessage());
        this.error = error;
    }
}
