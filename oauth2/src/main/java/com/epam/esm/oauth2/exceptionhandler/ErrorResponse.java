package com.epam.esm.oauth2.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class ErrorResponse {
    private String message;

    public ErrorResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
