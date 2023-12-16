package com.rhinitis.projectrhinitis.util.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity handleBusinessException(BusinessLogicException exception){

        return new ResponseEntity<>(new ErrorResponse(exception),HttpStatus.BAD_REQUEST);
    }
}
