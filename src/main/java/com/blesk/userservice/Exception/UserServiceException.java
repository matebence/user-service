package com.blesk.userservice.Exception;

import org.springframework.http.HttpStatus;

public class UserServiceException extends RuntimeException {

    private HttpStatus httpStatus;

    public UserServiceException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}