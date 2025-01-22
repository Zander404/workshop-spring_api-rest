package com.xandy.spring_rest.exceptions;

public class UsernameUniqueViolationException extends RuntimeException {
    public UsernameUniqueViolationException(String message  ) {
        super(message);
    }
}
