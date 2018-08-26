package com.rejoice.entity.json;

import com.rejoice.entity.user.User;
import com.fasterxml.jackson.annotation.JsonView;


public class Error {

    @JsonView(value = {User.SignInRepresentation.class})
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Error(String message) {
        this.message = message;
    }

    public Error() {
    }
}
