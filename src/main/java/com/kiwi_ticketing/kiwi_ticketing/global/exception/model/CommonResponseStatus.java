package com.kiwi_ticketing.kiwi_ticketing.global.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonResponseStatus {
    /**
     * 성공 반환 2000번대
     */
    SUCCESS(2000, "요청에 성공하였습니다.",HttpStatus.OK),

    /**
     * 클라이언트 에러 4000번대
     */
    BAD_REQUEST(4000, "클라이언트의 잘못된 요청입니다.", HttpStatus.BAD_REQUEST),

    /**
     * 서버 에러 5000번대
     */
    INTERNAL_SERVER_ERROR(5000, "서버에서 알 수 없는 에러가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    CommonResponseStatus(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
