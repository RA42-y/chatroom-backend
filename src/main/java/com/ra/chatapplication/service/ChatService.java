package com.ra.chatapplication.service;

import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.model.request.ChatJoinRequest;

import java.util.List;

public interface ChatService {

//    Chat saveChat(Chat chat);

    Chat addMemberToChat(Chat chat, User member);

    List<Chat> getChatsCreatedByUser(User user);

    List<Chat> getChatsOfUser(User user);

    Chat saveChat(Chat chat);

    void deleteChat(Chat chat);

    boolean joinChat(ChatJoinRequest chatJoinRequest, User loginUser);

    long createChat(Chat chat, User loginUser);
}
