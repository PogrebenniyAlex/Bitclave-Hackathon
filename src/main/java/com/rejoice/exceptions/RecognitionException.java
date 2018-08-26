package com.rejoice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.CONFLICT)
public class RecognitionException extends Exception {
    public RecognitionException(String s) {
        super(s);
    }
}
