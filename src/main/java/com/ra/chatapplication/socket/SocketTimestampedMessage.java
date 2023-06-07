package com.ra.chatapplication.socket;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketTimestampedMessage implements Serializable {

    private String type;

    private String email;

    private String message;

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

