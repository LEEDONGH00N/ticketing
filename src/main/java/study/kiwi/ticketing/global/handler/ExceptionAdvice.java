package study.kiwi.ticketing.global.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.kiwi.ticketing.global.codes.ErrorCode;
import study.kiwi.ticketing.global.common.ApiResponse;
import study.kiwi.ticketing.global.common.BaseException;
import study.kiwi.ticketing.global.codes.reason.Reason.ReasonDto;


import static study.kiwi.ticketing.global.codes.ErrorCode.*;


@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<Object> validation(MethodArgumentNotValidException e) {
        //TODO : 좀 더 깔쌈하게 만들어야 함
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        ApiResponse<?> apiResponse = ApiResponse.onFailure(BINDING_ERROR.getCode(), message, null);
        return handleExceptionInternalFalse(apiResponse);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> onThrowException(BaseException baseException) {
        ReasonDto errorReasonHttpStatus = baseException.getErrorReasonHttpStatus();
        ApiResponse<?> apiResponse = ApiResponse.onFailure(errorReasonHttpStatus.code(), errorReasonHttpStatus.message(), null);
        return handleExceptionInternalFalse(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e) {
        log.error(e.getMessage());
        ApiResponse<?> baseResponse = ApiResponse.onFailure(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), null);
        return handleExceptionInternalFalse(baseResponse);
    }

    private ResponseEntity<Object> handleExceptionInternalFalse(ApiResponse<?> response) {
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}