package study.kiwi.ticketing.global.codes.reason;

import lombok.*;
import org.springframework.http.HttpStatus;

public class Reason {
    @Builder
    public record ReasonDto(
            HttpStatus httpStatus,
            String code,
            String message,
            Boolean isSuccess
    ){ }
}