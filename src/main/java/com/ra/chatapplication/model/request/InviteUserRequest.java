package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Invite user to chat request body
 */
@Data
public class InviteUserRequest implements Serializable {

    /**
     * Chat id
     */
    private Long userId;


}
