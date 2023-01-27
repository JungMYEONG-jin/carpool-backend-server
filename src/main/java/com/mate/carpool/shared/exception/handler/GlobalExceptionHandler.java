package com.mate.carpool.shared.exception.handler;

import com.mate.carpool.shared.dto.ErrorResponse;
import com.mate.carpool.shared.exception.CustomHttpException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomHttpException.class)
    public ErrorResponse customHttpException(CustomHttpException exception) {
        return new ErrorResponse(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
    }
}

