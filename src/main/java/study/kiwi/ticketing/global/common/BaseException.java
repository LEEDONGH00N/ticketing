package study.kiwi.ticketing.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.kiwi.ticketing.global.codes.BaseCode;
import study.kiwi.ticketing.global.codes.reason.Reason;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private BaseCode code;

    public Reason.ReasonDto getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}