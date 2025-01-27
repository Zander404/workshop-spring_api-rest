package com.xandy.spring_rest.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class CpfUniqueException extends RuntimeException {
    public CpfUniqueException(String message) {
        super(message);
    }
}
