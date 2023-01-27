package com.mate.carpool.shared.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public
class ErrorResponse {
    private HttpStatus status;
    private String message;

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}