package org.example.common.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.BaseResponse;
import org.example.exception.model.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.example.exception.ErrorCode;

@Slf4j
@RestControllerAdvice
@Component
@RequiredArgsConstructor
public class ControllerExceptionAdvice {

    /**
     * 400 BAD_REQUEST
     */
    // 필수 헤더 누락 시
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public BaseResponse handleMissingRequestHeaderException(final MissingRequestHeaderException e) {
        log.error("Missing Request Header: {}", e.getMessage(), e);
        return BaseResponse.error(ErrorCode.VALIDATION_REQUEST_HEADER_MISSING_EXCEPTION, String.format("%s. (%s)", ErrorCode.VALIDATION_REQUEST_HEADER_MISSING_EXCEPTION.getMessage(), e.getHeaderName()));
    }

    // 필수 파라미터 누락 시
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResponse handleMissingRequestParameterException(final MissingServletRequestParameterException e) {
        log.error("Missing Request Parameter: {}", e.getMessage(), e);
        return BaseResponse.error(ErrorCode.VALIDATION_REQUEST_PARAMETER_MISSING_EXCEPTION, String.format("%s. (%s)", ErrorCode.VALIDATION_REQUEST_PARAMETER_MISSING_EXCEPTION.getMessage(), e.getParameterName()));
    }

    // 지원하지 않는 요청 메소드로 지원 시
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.error("Http Request Method Not Supported: {}", e.getMessage(), e);
        return BaseResponse.error(ErrorCode.REQUEST_METHOD_VALIDATION_EXCEPTION, e.getMessage());
    }

//    // Validation 관련 예외 처리: 추후 추가 예정
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected BaseResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
//        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
//        log.error("Validation error for field {}: {}", fieldError.getField(), fieldError.getDefaultMessage());
//        return BaseResponse.error(ErrorCode.VALIDATION_REQUEST_MISSING_EXCEPTION, String.format("%s. (%s)", fieldError.getDefaultMessage(), fieldError.getField()));
//    }

//    // 인증 관련 예외 처리: 추후 추가 예정
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(BadCredentialsException.class)
//    protected BaseResponse badCredentialsException(final BadCredentialsException e) {
//        log.error("Bad Credentials: {}", e.getMessage(), e);
//        return BaseResponse.error(ErrorCode.AUTHORIZE_FAILED_EXCEPTION, ErrorCode.AUTHORIZE_FAILED_EXCEPTION.getMessage());
//    }

    /**
     * 413 Payload Too Large
     */
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
//    public BaseResponse fileSizeLimitExceeded(final MaxUploadSizeExceededException e) {
//        log.error("File Size Limit Exceeded: {}", e.getMessage(), e);
//        return BaseResponse.error(ErrorCode.MAX_UPLOAD_SIZE_EXCEED_EXCEPTION, e.getMessage());
//    }

    /**
     * 500 Internal Server Error
     */
    // 원인 모를 이유의 예외 발생 시
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResponse handleServerException(final Exception e) {
        log.error("Internal Server Error: {}", e.getMessage(), e);
        return BaseResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * custom error
     */
    // 내부 커스텀 에러 처리하기
    @ExceptionHandler(CustomException.class)
    public BaseResponse handleCustomException(CustomException e) {
        log.error("CustomException: {}", e.getMessage(), e);
        return BaseResponse.error(e.getErrorCode(), e.getMessage());
    }
}



