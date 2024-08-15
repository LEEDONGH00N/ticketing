package study.kiwi.ticketing.global.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import study.kiwi.ticketing.global.common.ApiResponse;
import study.kiwi.ticketing.global.common.BaseException;
import study.kiwi.ticketing.global.common.reason.Reason.ReasonDto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static study.kiwi.ticketing.global.common.codes.ErrorCode.*;


@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @Override
    @Order(0)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        log.info("HttpRequestMethodNotSupportedException: {}", message);
        ApiResponse<?> baseResponse = ApiResponse.onFailure("METHOD_NOT_SUPPORTED", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("MEDIA_TYPE_NOT_SUPPORTED", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("MEDIA_TYPE_NOT_ACCEPTABLE", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("MISSING_PATH_VARIABLE", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("MISSING_REQUEST_PARAMETER", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("MISSING_REQUEST_PART", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("REQUEST_BINDING_ERROR", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("HANDLER_METHOD_VALIDATION_ERROR", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("NO_RESOURCE_FOUND", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("ASYNC_REQUEST_TIMEOUT", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("ERROR_RESPONSE_EXCEPTION", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("MAX_UPLOAD_SIZE_EXCEEDED", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("CONVERSION_NOT_SUPPORTED", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("TYPE_MISMATCH", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("MESSAGE_NOT_READABLE", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("MESSAGE_NOT_WRITABLE", message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ProblemDetail createProblemDetail(Exception ex, HttpStatusCode status, String defaultDetail, String detailMessageCode, Object[] detailMessageArguments, WebRequest request) {
        String message = ex.getMessage();
        return super.createProblemDetail(ex, status, defaultDetail, detailMessageCode, detailMessageArguments, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        String message = ex.getMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure("INTERNAL_ERROR", message, null);
        return handleExceptionInternalFalse(baseResponse,statusCode);
    }


    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        ApiResponse<?> baseResponse = ApiResponse.onFailure(BINDING_ERROR.getCode(), message, null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiResponse<?> baseResponse = ApiResponse.onFailure(INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), null);
        return handleExceptionInternalFalse(baseResponse, status);
    }

//    @ExceptionHandler
//    public ResponseEntity<Object> validation(MethodArgumentNotValidException e) {
//        String message = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
//        BaseResponse<?> baseResponse = BaseResponse.onFailure(BINDING_ERROR.getCode(), message, null);
//        return handleExceptionInternalFalse(baseResponse);
//    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> onThrowException(BaseException baseException) {
        ReasonDto errorReasonHttpStatus = baseException.getErrorReasonHttpStatus();
        ApiResponse<?> apiResponse = ApiResponse.onFailure(errorReasonHttpStatus.getCode(), errorReasonHttpStatus.getMessage(), null);
        return handleExceptionInternalFalse(apiResponse, errorReasonHttpStatus.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e) {
        ApiResponse<?> apiResponse = ApiResponse.onFailure(INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), null);
        return handleExceptionInternalFalse(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handleExceptionInternalFalse(ApiResponse<?> response, HttpStatusCode statusCode) {
        return new ResponseEntity<>(response, statusCode);
    }
//
//    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCommonStatus) {
//        BaseResponse<?> baseResponse = BaseResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), null);
//        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
//    }
}