package com.example.demo.Model;

public class RepostingResponse {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RepostingResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
