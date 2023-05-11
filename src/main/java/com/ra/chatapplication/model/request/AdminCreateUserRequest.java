package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminCreateUserRequest implements Serializable {

    private String email;

    private String firstName;

    private String lastName;

    private Boolean admin;
}
