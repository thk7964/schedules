package com.example.schedules.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice//모든 컨드롤러에서 발생하는 예외를 한 곳에서 모아 처리한다.
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    protected ResponseEntity<ErrorResponseEntity> handleGlobalException(GlobalException e){
       return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }
}
