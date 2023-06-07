package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Request body for user to reset password
 */
@Data
public class UserResetPasswordRequest implements Serializable {

    /**
     * New password
     */
    private String passwordNew;

    /**
     * Password of validation
     */
    private String passwordValidation;

    public UserResetPasswordRequest() {
    }
}
