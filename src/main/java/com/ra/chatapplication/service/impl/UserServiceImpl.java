package com.ra.chatapplication.service.impl;

import com.ra.chatapplication.dao.UserRepository;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.service.UserService;
import com.ra.chatapplication.utils.EmailUtils;
import com.ra.chatapplication.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailUtils emailUtils;

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

    public User createUserByAdmin(String firstName, String lastName, String email, Boolean admin) throws MessagingException {
        String password = PasswordUtils.generateRandomPassword(8);
        User user = new User(firstName, lastName, email, password, admin);
        userRepository.save(user);
        System.out.println(user.getEmail());
        System.out.println(user.getFirstName());
        emailUtils.sendDefaultPasswordHtmlMail(user, password);
        return user;
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

    public User findCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmailIgnoreCase(email);
    }

}
