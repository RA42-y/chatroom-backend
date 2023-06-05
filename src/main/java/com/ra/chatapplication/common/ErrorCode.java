package com.ra.chatapplication.common;


public enum ErrorCode {

    SUCCESS(0, "ok", ""),

    PARAMS_ERROR(40000, "Request parameter error", ""),

    NULL_ERROR(40001, "Request data is empty", ""),

    NOT_LOGIN(40100, "Not logged in", ""),

    NO_AUTH(40101, "Unauthorized", ""),

    INVALID_TOKEN(40102, "Token invalid", ""),

    FORBIDDEN(40301, "Forbidden access", ""),

    SYSTEM_ERROR(50000, "Internal server error", "");

    private final int code;

    private final String message;

    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
