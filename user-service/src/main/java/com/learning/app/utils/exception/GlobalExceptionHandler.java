package com.learning.app.utils.exception;

import com.learning.app.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorResponse<Object>> handleApiException(ApiException e) {
        ErrorResponse<Object> response = new ErrorResponse<>();
        response.setStatus("fail");
        response.setHttpStatus(e.getHttpStatus());
        response.setErrorCode(e.getErrorCode());
        response.setErrorMessage(e.getMessage());
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getHttpStatus()));
    }
}
