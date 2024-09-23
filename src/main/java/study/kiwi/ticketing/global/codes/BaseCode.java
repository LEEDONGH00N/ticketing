package study.kiwi.ticketing.global.codes;

import study.kiwi.ticketing.global.codes.reason.Reason;

public interface BaseCode {
    Reason.ReasonDto getReasonHttpStatus();
}
