package com.vinayak.secure_rest_api.advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ErrorResponse {
    private String message;
    private Map<String, String> errors;
    private String error;
    private int status;
    private LocalDateTime timestamp;

    public ErrorResponse(){
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String message, Map<String, String> errors, HttpStatus status) {
        this();
        this.message = message;
        this.errors = errors;
        this.status = status.value();
        this.error = status.getReasonPhrase();
    }

    public ErrorResponse(String message, HttpStatus status) {
        this();
        this.message = message;
        this.status = status.value();
        this.error = status.getReasonPhrase();
    }

}
