package com.ra.chatapplication.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatJoinRequest implements Serializable {

    private Long chatId;

    private Long userId;
}
