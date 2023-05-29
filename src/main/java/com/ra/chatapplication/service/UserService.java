package com.ra.chatapplication.service;

import com.ra.chatapplication.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    User userLogin(String email, String password, HttpServletRequest request);

    User getUserById(long id);

    List<User> getAllUsers();

    Page<User> getAllUsersByPage(int pageNumber, int pageSize, String sortBy);

    Page<User> getAllDeactivatedUsersByPage(int pageNumber, int pageSize, String sortBy);

    List<User> getAllDeactivatedUsers();

    User createUser(String firstName, String lastName, String email, String password, Boolean admin);

    User createUserByAdmin(String firstName, String lastName, String email, Boolean admin) throws MessagingException;

    User saveUser(User user);

    void deleteUser(long id);

    User deactivateUser(long id);

    User activateUser(long id);

    User resetUserFailureTimes(long id);

    Page<User> searchUsers(String keyword, int pageNumber, int pageSize, String sortBy);

    Page<User> searchDeactivatedUsers(String keyword, int pageNumber, int pageSize, String sortBy);

    List<User> findUsersByFirstName(String firstName);

    List<User> findUsersByLastName(String lastName);

    User getUserByEmail(String email);

    User findCurrentUser();

    User getSafetyUser(User originUser);

    User getLoginUser(HttpServletRequest request);

    boolean comparePasswords(String rawPassword, String encodedPassword);
}
