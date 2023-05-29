package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Join chat request body
 */
@Data
public class ChatJoinRequest implements Serializable {

    /**
     * Chat id
     */
    private Long chatId;
}
