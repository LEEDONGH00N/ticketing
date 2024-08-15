package study.kiwi.ticketing.global.common.codes;

import study.kiwi.ticketing.global.common.reason.Reason;

public interface BaseCode {
    Reason.ReasonDto getReasonHttpStatus();
}
