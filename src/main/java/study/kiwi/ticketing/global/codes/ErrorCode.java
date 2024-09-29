package study.kiwi.ticketing.global.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import study.kiwi.ticketing.global.codes.reason.Reason;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseCode {

    // 4xx : client error
    EMAIL_ALREADY_EXISTS(HttpStatus.FORBIDDEN, "MEMBER-0002", "이메일이 존재합니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN-0001", "토큰ㅇ"),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN-0000", "토큰 오류"),
    INVALID_EMAIL_OR_PASSWORD(HttpStatus.NOT_FOUND, "MEMBER-0001", "유효하지 않는 이메일, 비번"),
    EMPTY_TOKEN_PROVIDED(HttpStatus.UNAUTHORIZED, "TOKEN-0002", "토큰 텅텅"),
    FAIL_PROCEED(HttpStatus.FORBIDDEN, "FAIL", ""),
    PASSWORD_ERROR(HttpStatus.BAD_REQUEST, "LOGIN-0000", "잘못된 비밀번호입니다."),

    BINDING_ERROR(HttpStatus.BAD_REQUEST, "BINDING-0000", "바인딩에 실패했습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-0000", "회원이 없습니다."),


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
