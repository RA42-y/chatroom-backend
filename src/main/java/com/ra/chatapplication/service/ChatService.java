package com.ra.chatapplication.service;

import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.model.request.ChatJoinRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChatService {

//    Chat saveChat(Chat chat);

    Chat addMemberToChat(Chat chat, User member);

    List<Chat> getChatsCreatedByUser(User user);

    List<Chat> getChatsJoinedByUser(User user);

    Page<Chat> getChatsCreatedByUserByPage(User user, int pageNumber, int pageSize);

    Page<Chat> getChatsJoinedByUserByPage(User user, int pageNumber, int pageSize);

    Chat removeCreator(Chat chat);

    Chat removeUserFromChat(Chat chat, User user);

    Chat saveChat(Chat chat);

//    Page<User> getAllUsersByPage(int pageNumber, int pageSize, String sortBy);

    void deleteChat(Chat chat);

    boolean joinChat(ChatJoinRequest chatJoinRequest, User loginUser);

    Chat createChat(String name, String description, User creator);
}
