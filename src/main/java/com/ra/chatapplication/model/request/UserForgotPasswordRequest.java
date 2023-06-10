package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Request body for
 */
@Data
public class UserForgotPasswordRequest implements Serializable {

    /**
     * Email
     */
    private String email;

    public UserForgotPasswordRequest() {
    }
}
