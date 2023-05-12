package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminEditUserRequest implements Serializable {

    private String email;

    private String firstName;

    private String lastName;

    private Boolean admin;

    public AdminEditUserRequest(String email, String firstName, String lastName, Boolean admin) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.admin = admin;
    }
}
