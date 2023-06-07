package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Request body for creating a chat
 */
@Data
public class ChatCreateRequest implements Serializable {

    /**
     * Chat name
     */
    private String name;

    /**
     * Chat description
     */
    private String description;
}
