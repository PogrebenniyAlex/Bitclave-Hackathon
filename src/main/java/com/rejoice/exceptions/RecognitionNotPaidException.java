package com.rejoice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.PAYMENT_REQUIRED)
public class RecognitionNotPaidException extends Exception {
    public RecognitionNotPaidException(String s) {
        super(s);
    }
}
