package com.xandy.spring_rest.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j


@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValid(AccessDeniedException exception, HttpServletRequest request, BindingResult result) {

        log.error("Api Error - ", exception);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, exception.getMessage()));

    }

    @ExceptionHandler(UsernameUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValid(RuntimeException exception, HttpServletRequest request, BindingResult result) {

        log.error("Api Error - ", exception);
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Field",  result));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValid(MethodArgumentNotValidException exception, HttpServletRequest request, BindingResult result) {

        log.error("Api Error - ", exception);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Field",  result));

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFound(RuntimeException exception, HttpServletRequest request, BindingResult result) {
        log.error("Api Error - ", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Field",  result));

    }
}
