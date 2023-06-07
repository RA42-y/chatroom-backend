package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Request body for editing a user by admin
 */
@Data
public class AdminEditUserRequest implements Serializable {

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

    public AdminEditUserRequest(String email, String firstName, String lastName, Boolean admin) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.admin = admin;
    }
}
