package com.ra.chatapplication.service;

import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChatService {

//    Chat saveChat(Chat chat);

    Page<Chat> getAllChatsByPage(int pageNumber, int pageSize);

    Chat getChatById(long id);

    Chat addMemberToChat(Chat chat, User member);

    List<Chat> getChatsCreatedByUser(User user);

    List<Chat> getChatsJoinedByUser(User user);

    Page<Chat> getChatsCreatedByUserByPage(User user, int pageNumber, int pageSize);

    Page<Chat> getChatsJoinedByUserByPage(User user, int pageNumber, int pageSize);

    Page<Chat> getChatsOfUserByPage(User user, int pageNumber, int pageSize);

    Chat removeCreator(Chat chat);

    Chat removeUserFromChat(Chat chat, User user);

    Chat saveChat(Chat chat);

//    Page<User> getAllUsersByPage(int pageNumber, int pageSize, String sortBy);

    void deleteChat(Chat chat);

    Chat editChat(Chat chat, String name, String description);

    Chat createChat(String name, String description, User creator);

    boolean isUserCreator(Chat chat, User user);

    boolean isUserMember(Chat chat, User user);
}
