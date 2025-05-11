package com.learning.app.model.dto;

public class SuccessResponse<T> {
    private String status;
    private int httpStatus;
    private String message;
    private T data;

    public SuccessResponse(String status, int httpStatus, String message, T data) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }
    public SuccessResponse() {}

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
