package com.unbosque.paseadores.core.exceptions;

public class BadAuthenticationException extends RuntimeException {
    public BadAuthenticationException(String message) {
        super(message);
    }
}
