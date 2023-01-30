package com.mate.carpool.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseData<T> extends CommonResponse {
    private T data;

    public ResponseData(HttpStatus status, String message, T data) {
        super(status, message);
        this.data = data;
    }

    public ResponseData(HttpStatus status, String message) {
        super(status, message);
    }
}
