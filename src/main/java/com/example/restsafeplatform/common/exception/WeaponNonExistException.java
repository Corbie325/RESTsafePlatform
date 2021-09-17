package com.example.restsafeplatform.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class WeaponNonExistException extends Exception{
    public WeaponNonExistException(String msg) {super(msg);}
}
