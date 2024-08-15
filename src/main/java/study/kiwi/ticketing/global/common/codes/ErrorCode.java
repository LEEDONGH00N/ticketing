package study.kiwi.ticketing.global.common.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import study.kiwi.ticketing.global.common.reason.Reason;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseCode {

    // 4xx : client error

    //자잘한 에러
    SEARCH_KEYWORD_TOO_SHORT(HttpStatus.BAD_REQUEST, "KEYWORD-0000", "검색어는 3글자부터 입력하세요."),

    //바인딩 에러
    BINDING_ERROR(HttpStatus.BAD_REQUEST, "BINDING-0000", "바인딩에 실패했습니다."),

    //로그인 에러
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "LOGIN-0001", "이메일이 잘못됨"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON-0000", "잘못된 요청입니다."),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "COMMON-0002", "이미 존재하는 회원입니다."),
    /*
    *
    *
    * */

    // 5xx : server error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER-0000", "서버 에러");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public Reason.ReasonDto getReasonHttpStatus() {
        return Reason.ReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
