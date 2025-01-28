package com.xandy.spring_rest.exceptions;

public class CodeUniqueViolation extends RuntimeException {
    public CodeUniqueViolation(String message) {
        super(message);
    }
}
