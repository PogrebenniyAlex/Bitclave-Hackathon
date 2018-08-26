package com.rejoice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class WrongEmailOrPasswordException extends Exception{

    public WrongEmailOrPasswordException(String message) {
        super(message);
    }

    public WrongEmailOrPasswordException() {
    }

    public WrongEmailOrPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongEmailOrPasswordException(Throwable cause) {
        super(cause);
    }

    public WrongEmailOrPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }
}
