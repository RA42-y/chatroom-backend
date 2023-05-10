package com.ra.chatapplication.service.impl;

import com.ra.chatapplication.service.UserService;

import com.ra.chatapplication.model.User;
import com.ra.chatapplication.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User userLogin(String email, String password) {
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllDeactivatedUsers() {
        return userRepository.findByActiveFalse();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User editUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public List<User> findUsersByFirstName(String firstName) {
        return userRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    public List<User> findUsersByLastName(String lastName) {
        return userRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

}
