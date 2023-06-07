package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Request body fot inviting a user to chat
 */
@Data
public class InviteUserRequest implements Serializable {

    /**
     * User ID
     */
    private Long userId;


}
