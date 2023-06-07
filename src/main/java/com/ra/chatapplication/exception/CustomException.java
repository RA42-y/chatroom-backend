package com.ra.chatapplication.exception;


import com.ra.chatapplication.common.ErrorCode;

/**
 * Custom exception class
 */
public class CustomException extends RuntimeException {

    /**
     * Exception code
     */
    private final int code;

    /**
     * Exception description
     */
    private final String description;

    public CustomException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public CustomException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
