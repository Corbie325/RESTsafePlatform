package com.example.restsafeplatform.common.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AlreadyExistException extends DataAccessException {

    public AlreadyExistException(String msg) {
        super(msg);
    }

    public AlreadyExistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
