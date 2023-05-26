package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatCreateRequest implements Serializable {

    private String name;

    private String description;

    private Long userId;
}
