package com.ra.chatapplication.service.impl;

import com.ra.chatapplication.dao.ChatRepository;
import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.model.request.ChatJoinRequest;
import com.ra.chatapplication.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Page<Chat> getAllChatsByPage(int pageNumber, int pageSize) {
        Pageable pageable;
        pageable = PageRequest.of(pageNumber, pageSize);
        return chatRepository.findAll(pageable);
    }

    @Override
    public Chat getChatById(long id) {
        return chatRepository.findChatById(id);
    }

    @Override
    public Chat addMemberToChat(Chat chat, User member) {
        chat.getMembers().add(member);
        return chat;
    }

    @Override
    public List<Chat> getChatsCreatedByUser(User user) {
        return chatRepository.findByCreator(user);
    }

    @Override
    public List<Chat> getChatsJoinedByUser(User user) {
        return chatRepository.findByMembersContaining(user);
    }

    @Override
    public Page<Chat> getChatsCreatedByUserByPage(User user, int pageNumber, int pageSize) {
        Pageable pageable;
        pageable = PageRequest.of(pageNumber, pageSize);
        return chatRepository.findByCreator(user, pageable);
    }

    @Override
    public Page<Chat> getChatsJoinedByUserByPage(User user, int pageNumber, int pageSize) {
        Pageable pageable;
        pageable = PageRequest.of(pageNumber, pageSize);
        return chatRepository.findByMembersContaining(user, pageable);
    }

    @Override
    public Chat removeCreator(Chat chat) {
        chat.setCreator(null);
        return chat;
    }

    @Override
    public Chat removeUserFromChat(Chat chat, User user) {
        List<User> members = chat.getMembers();
        members.remove(user);
        return chat;
    }

    @Override
    public Chat saveChat(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public void deleteChat(Chat chat) {
        chatRepository.delete(chat);
    }


    @Override
    public boolean joinChat(ChatJoinRequest chatJoinRequest, User loginUser) {
        Chat chat = chatRepository.findChatById(chatJoinRequest.getChatId());
        addMemberToChat(chat, loginUser);
        return true;
    }

    @Override
    public Chat createChat(String name, String description, User creator) {
        Chat chat = new Chat(name, description, creator);
        chatRepository.save(chat);
        return chat;
    }

    @Override
    public Chat editChat(Chat chat, String name, String description) {
        chat.setName(name);
        chat.setDescription(description);
        return chat;
    }

    @Override
    public boolean isUserCreator(Chat chat, User user) {
        return user == chat.getCreator();
    }

    @Override
    public boolean isUserMember(Chat chat, User user) {
        List<User> member = chat.getMembers();
        return member.contains(user);
    }
}


