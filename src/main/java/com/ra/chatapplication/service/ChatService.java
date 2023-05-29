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

    Chat removeCreator(Chat chat);

    Chat removeUserFromChat(Chat chat, User user);

    Chat saveChat(Chat chat);

    void deleteChat(Chat chat);

    boolean joinChat(ChatJoinRequest chatJoinRequest, User loginUser);

    Chat createChat(String name, String description, User creator);
}
