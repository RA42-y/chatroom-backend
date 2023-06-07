package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Request body for creating a user by admin
 */
@Data
public class AdminCreateUserRequest implements Serializable {

    /**
     * Email
     */
    private String email;

    /**
     * First name
     */
    private String firstName;

    /**
     * Last name
     */
    private String lastName;

    /**
     * User role
     */
    private Boolean admin;
}
