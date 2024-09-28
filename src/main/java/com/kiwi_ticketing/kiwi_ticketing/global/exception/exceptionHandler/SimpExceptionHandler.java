package com.kiwi_ticketing.kiwi_ticketing.global.exception.exceptionHandler;

import com.kiwi_ticketing.kiwi_ticketing.global.exception.model.CommonException;
import com.kiwi_ticketing.kiwi_ticketing.global.exception.ErrorWrapper;
import com.kiwi_ticketing.kiwi_ticketing.global.exception.model.SimpErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SimpExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorWrapper<SimpErrorResponse>> commonExceptionHandle(CommonException e) {
        SimpErrorResponse errorReport = new SimpErrorResponse(e.getError().getCode(), e.getMessage());
        ErrorWrapper<SimpErrorResponse> error = new ErrorWrapper<>(errorReport);
        return new ResponseEntity<>(error, e.getError().getStatusCode());
    }
}
