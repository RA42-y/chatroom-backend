package com.ra.chatapplication.service.impl;

import com.ra.chatapplication.dao.ChatRepository;
import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void addMemberToChat(Chat chat, User member) {
        chat.getMembers().add(member);
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
    public Page<Chat> getChatsOfUserByPage(User user, int pageNumber, int pageSize) {
        Pageable pageable;
        pageable = PageRequest.of(pageNumber, pageSize);
        return chatRepository.findByMembersContainingOrCreator(user, user, pageable);
    }

    @Override
    public void removeCreator(Chat chat) {
        chat.setCreator(null);
    }

    @Override
    public void removeUserFromChat(Chat chat, User user) {
        List<User> members = chat.getMembers();
        members.remove(user);
    }

    @Override
    public void saveChat(Chat chat) {
        chatRepository.save(chat);
    }

    @Override
    public void deleteChat(Chat chat) {
        List<User> members = chat.getMembers();
        members = new ArrayList<>();
//        Iterator<User> iterator = members.iterator();
//        while (iterator.hasNext()) {
//            User member = iterator.next();
//            removeUserFromChat(chat, member);
//            iterator.remove();
//        }
        chatRepository.save(chat);
        chatRepository.delete(chat);
    }

    @Override
    public Chat createChat(String name, String description, Date expireDate, User creator) {
        Chat chat = new Chat(name, description, expireDate, creator);
        chatRepository.save(chat);
        return chat;
    }

    @Override
    public void editChat(Chat chat, String name, String description, Date expireDate) {
        chat.setName(name);
        chat.setDescription(description);
        chat.setExpireDate(expireDate);
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


