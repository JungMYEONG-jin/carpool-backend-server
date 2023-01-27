package com.mate.carpool.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomHttpException extends RuntimeException{
    private HttpStatus status;

    public CustomHttpException() {
        super();
    }

    public CustomHttpException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
