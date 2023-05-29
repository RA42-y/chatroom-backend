package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserResetPasswordRequest implements Serializable {

    private String passwordNew;

    private String passwordValidation;

    public UserResetPasswordRequest() {
    }
}
