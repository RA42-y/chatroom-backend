package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Request body for user to log in
 */
@Data
public class UserLoginRequest implements Serializable {

    /**
     * Email
     */
    private String email;

    /**
     * Password
     */
    private String password;
}
