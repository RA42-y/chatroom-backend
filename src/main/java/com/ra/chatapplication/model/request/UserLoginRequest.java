package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private String email;

    private String password;
}
