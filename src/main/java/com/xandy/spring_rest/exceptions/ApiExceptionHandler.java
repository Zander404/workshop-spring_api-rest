package com.xandy.spring_rest.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        log.error("Api Error - ", exception);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, exception.getMessage()));

    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> passwordInvalidException(RuntimeException exception, HttpServletRequest request) {

        log.error("Api Error - ", exception);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage()));
    }

    @ExceptionHandler({UsernameUniqueViolationException.class, CpfUniqueException.class})
    public ResponseEntity<ErrorMessage> usernameUniqueViolationException(RuntimeException exception, HttpServletRequest request) {

        log.error("Api Error - ", exception);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, exception.getMessage()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValid(MethodArgumentNotValidException exception, HttpServletRequest request, BindingResult result) {

        log.error("Api Error - ", exception);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY,
                        messageSource.getMessage("message.invalid.field", null, request.getLocale()),
                        result,
                        messageSource));

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFound(RuntimeException exception, HttpServletRequest request) {
        log.error("Api Error - ", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(request, HttpStatus.NOT_FOUND, exception.getMessage()));

    }
}
