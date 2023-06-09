package com.ra.chatapplication.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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

    /**
     * Date of expiration
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
    private Date expireDate;
}
