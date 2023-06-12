package com.ra.chatapplication.websocket;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a timestamped message received during Web Socket communication
 */
public class SocketTimestampedMessage implements Serializable {

    /**
     * Type of the message
     */
    private String type;

    /**
     * Email of the sender
     */
    private String email;

    /**
     * Content of the message
     */
    private String message;

    /**
     * Timestamp of the message
     */
    private String timestamp;

    public SocketTimestampedMessage(String type, String email, String message) {
        this.type = type;
        this.email = email;
        this.message = message;
        this.timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        System.out.println(this.timestamp);
    }

    public SocketTimestampedMessage(String type, String email) {
        System.out.println(type);
        System.out.println(email);
        this.type = type;
        this.email = email;
        this.timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

}

