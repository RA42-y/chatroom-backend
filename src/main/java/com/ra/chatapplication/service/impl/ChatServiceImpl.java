package com.ra.chatapplication.service.impl;

import com.ra.chatapplication.dao.ChatRepository;
import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.model.request.ChatJoinRequest;
import com.ra.chatapplication.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat addMemberToChat(Chat chat, User member) {
        chat.getMembers().add(member);
        return chatRepository.save(chat);
    }

    @Override
    public List<Chat> getChatsCreatedByUser(User user) {
        return chatRepository.findByCreator(user);
    }

    @Override
    public List<Chat> getChatsOfUser(User user) {
        return chatRepository.findByMembersContaining(user);
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
    public long createChat(Chat chat, User loginUser) {
        chat.setId(null);
        chat.setCreator(loginUser);
        chatRepository.save(chat);
        return chat.getId();
    }
}


