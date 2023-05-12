package com.ra.chatapplication.service;

import com.ra.chatapplication.model.entity.User;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {

    User userLogin(String email, String password);

    List<User> getAllUsers();

    List<User> getAllDeactivatedUsers();

    User createUser(User user);

    User createUserByAdmin(String firstName, String lastName, String email, Boolean admin) throws MessagingException;

    User editUser(User user);

    void deleteUser(long id);

    List<User> findUsersByFirstName(String firstName);

    List<User> findUsersByLastName(String lastName);

    User findUserByEmail(String email);

    User findCurrentUser();
}
