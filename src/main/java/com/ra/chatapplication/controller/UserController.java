package com.ra.chatapplication.controller;


import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.model.request.AdminCreateUserRequest;
import com.ra.chatapplication.model.request.AdminEditUserRequest;
import com.ra.chatapplication.model.request.UserLoginRequest;
import com.ra.chatapplication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import static com.ra.chatapplication.constant.UserConstant.USER_LOGIN_STATE;

/**
 * URL de base du endpoint : http://localhost:8080/admin<br>
 * ex users : http://localhost:8080/admin/users
 */
@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = {"http://localhost:5173"}, allowCredentials = "true")
@Slf4j
public class UserController {
    @Resource
    UserService userService;

//    @PostMapping("login")
//    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
//        String userAccount = userLoginRequest.getEmail();
//        String userPassword = userLoginRequest.getPassword();
//        User user = userService.userLogin(userAccount, userPassword, request);
//        return user;
//    }

    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
//            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        // TODO 校验用户是否合法
        User user = userService.getUserById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return safetyUser;
    }

    @GetMapping("user-info/{id}")
    public User getUserInfo(@PathVariable("id") long userId) {
        User user = userService.getUserById(userId);
        return userService.getSafetyUser(user);
    }
}
