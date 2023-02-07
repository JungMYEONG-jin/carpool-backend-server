package com.mate.carpool.shared.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class CommonResponse {
    private HttpStatus status;
    private String message;

    public static CommonResponse of(HttpStatus status, String message){
        return new CommonResponse(status, message);
    }
    public CommonResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
