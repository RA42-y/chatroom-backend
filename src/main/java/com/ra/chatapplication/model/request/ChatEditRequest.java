package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Request body for editing a chat
 */
@Data
public class ChatEditRequest implements Serializable {

    /**
     * Chat name
     */
    private String name;

    /**
     * Chat description
     */
    private String description;
}
