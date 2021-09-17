package com.example.restsafeplatform.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.security.sasl.AuthenticationException;

@Getter
public class JwtAuthenticationException extends AuthenticationException {
    private HttpStatus httpStatus;
    public JwtAuthenticationException(String msg) {
        super(msg);
    }
    public JwtAuthenticationException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}
