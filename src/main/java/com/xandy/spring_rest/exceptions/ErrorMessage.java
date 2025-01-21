package com.xandy.spring_rest.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {

    private String path;
    private String method;
    private int status;
    private String statusText;
    @Getter
    @Setter
    private String message;

    private Map<String, String> error;

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;

    }

    public ErrorMessage() {
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
        addErrors(result);

    }

    private void addErrors(BindingResult result) {
        this.error = new HashMap<>();
        for( FieldError fieldError : result.getFieldErrors()) {
            this.error.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

    }

}
