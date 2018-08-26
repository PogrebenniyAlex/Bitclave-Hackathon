package com.rejoice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.CONFLICT)
public class RefreshPasswordException extends Exception{

    public RefreshPasswordException(String message) {
        super(message);
    }

    public RefreshPasswordException() {
    }

    public RefreshPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshPasswordException(Throwable cause) {
        super(cause);
    }

    public RefreshPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
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
