package com.ra.chatapplication.service.impl;

import com.ra.chatapplication.common.ErrorCode;
import com.ra.chatapplication.dao.UserRepository;
import com.ra.chatapplication.exception.CustomException;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.service.UserService;
import com.ra.chatapplication.utils.EmailUtils;
import com.ra.chatapplication.utils.JwtUtils;
import com.ra.chatapplication.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.ra.chatapplication.constant.UserConstant.USER_LOGIN_STATE;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User userLogin(String email, String password, HttpServletRequest request) {
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user != null && user.getPassword().equals(password)) {
            User safetyUser = getSafetyUser(user);
            request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
            return safetyUser;
        }
        return null;
    }

    public User getUserById(long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> getAllUsersByPage(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable;
        if(sortBy.equals("")){
            pageable = PageRequest.of(pageNumber, pageSize);
        }else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        }
        return userRepository.findAll(pageable);
    }

    public Page<User> getAllDeactivatedUsersByPage(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable;
        if(sortBy.equals("")){
            pageable = PageRequest.of(pageNumber, pageSize);
        }else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        }
        return userRepository.findByActiveFalse(pageable);
    }

    public User createUser(String firstName, String lastName, String email, String password, Boolean admin) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(firstName, lastName, email, encodedPassword, admin);
        userRepository.save(user);
        return user;
    }

    public User createUserByAdmin(String firstName, String lastName, String email, Boolean admin) throws MessagingException {
        String password = PasswordUtils.generateRandomPassword(8);
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(firstName, lastName, email, encodedPassword, admin);
        userRepository.save(user);
        System.out.println(user.getEmail());
        System.out.println(user.getFirstName());
        emailUtils.sendDefaultPasswordHtmlMail(user, password);
        return user;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void editUser(User user, String firstName, String lastName, Boolean admin) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAdmin(admin);
        userRepository.save(user);
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public void deactivateUser(User user) {
        user.setActive(false);
        userRepository.save(user);
    }

    public void activateUser(User user) {
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void resetUserFailureTimes(User user) {
        user.setFailureTimes(0);
        userRepository.save(user);
    }

    public Page<User> searchUsers(String keyword, int pageNumber, int pageSize, String sortBy) {
        Pageable pageable;
        if(sortBy.equals("")){
            pageable = PageRequest.of(pageNumber, pageSize);
        }else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        }
        return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, keyword, pageable);
    }

    public Page<User> searchDeactivatedUsers(String keyword, int pageNumber, int pageSize, String sortBy) {
        Pageable pageable;
        if(sortBy.equals("")){
            pageable = PageRequest.of(pageNumber, pageSize);
        }else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        }
        return userRepository.findByFirstNameContainingIgnoreCaseAndActiveFalseOrLastNameContainingIgnoreCaseAndActiveFalseOrEmailContainingIgnoreCaseAndActiveFalse(keyword, keyword, keyword, pageable);
    }

    public User getUserByEmail(String email) {
        System.out.println(email);
        return userRepository.findByEmailIgnoreCase(email);
    }

    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setFirstName(originUser.getFirstName());
        safetyUser.setLastName(originUser.getLastName());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setAdmin(originUser.getAdmin());
        safetyUser.setActive(originUser.getActive());

        return safetyUser;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj == null) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        return (User) userObj;
    }

    @Override
    public User getLoginUserByToken(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String token = jwtUtils.extractTokenFromRequest(request);
        jwtUtils.validateJwtToken(token);
        String email = jwtUtils.getUserNameFromJwtToken(token);
        User currentUser = userRepository.findByEmailIgnoreCase(email);
        if (currentUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        return currentUser;
    }

    @Override
    public User getUserResetPasswordByToken(String token) {
        if (token == null) {
            return null;
        }
        jwtUtils.validateJwtToken(token);
        String email = jwtUtils.getUserNameFromJwtToken(token);
        User currentUser = userRepository.findByEmailIgnoreCase(email);
        if (currentUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        return currentUser;
    }

    public boolean comparePasswords(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
