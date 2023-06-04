package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Create chat request body
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
