package com.rejoice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecognitionNotFoundException extends Exception{

    public RecognitionNotFoundException(String message) {
        super(message);
    }

    public RecognitionNotFoundException() {
    }

    public RecognitionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecognitionNotFoundException(Throwable cause) {
        super(cause);
    }

    public RecognitionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
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
