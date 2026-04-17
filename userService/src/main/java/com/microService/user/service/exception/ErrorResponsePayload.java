package com.microService.user.service.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponsePayload {

    private String message;
    private HttpStatus status;
    private boolean success;

    public ErrorResponsePayload() {
    }

    public ErrorResponsePayload(String message, HttpStatus status, boolean success) {
        this.message = message;
        this.status = status;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
