package com.example.restsafeplatform.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.annotation.HttpConstraint;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNonExistException extends Exception {

    public UserNonExistException(String msg) {super(msg);}
}
