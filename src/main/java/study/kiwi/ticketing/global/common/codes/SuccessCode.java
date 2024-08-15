package study.kiwi.ticketing.global.common.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import study.kiwi.ticketing.global.common.reason.Reason;

@Getter
@AllArgsConstructor
public enum SuccessCode implements BaseCode{

    SUCCESS(HttpStatus.OK, "SUCCESS-0000", "요청에 성공하였습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public Reason.ReasonDto getReasonHttpStatus() {
        return null;
    }
}
