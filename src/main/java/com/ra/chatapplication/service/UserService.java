package com.ra.chatapplication.service;

import com.ra.chatapplication.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    List<User> getAllDeactivatedUsers();

    User createUser(User user);

    User editUser(User user);

    void deleteUser(long id);

    List<User> findUsersByFirstName(String firstName);

    List<User> findUsersByLastName(String lastName);

    User findUserByEmail(String email);
}
