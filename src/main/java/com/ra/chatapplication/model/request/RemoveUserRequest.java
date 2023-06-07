package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Request body fot removing a user from chat
 */
@Data
public class RemoveUserRequest implements Serializable {

    /**
     * User ID
     */
    private Long userId;


}
