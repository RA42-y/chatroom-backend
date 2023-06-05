package com.ra.chatapplication.controller;


import com.ra.chatapplication.common.BaseResponse;
import com.ra.chatapplication.common.ErrorCode;
import com.ra.chatapplication.common.ResultUtils;
import com.ra.chatapplication.exception.CustomException;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.model.request.AdminCreateUserRequest;
import com.ra.chatapplication.model.request.AdminEditUserRequest;
import com.ra.chatapplication.model.request.UserLoginRequest;
import com.ra.chatapplication.service.UserService;
//import com.ra.chatapplication.utils.TokenUtils;
import com.ra.chatapplication.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
@Slf4j
public class UserController {
    @Resource
    UserService userService;

//    @Autowired
//    private TokenUtils tokenUtils;

//    @PostMapping("login")
//    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
//        String userAccount = userLoginRequest.getEmail();
//        String userPassword = userLoginRequest.getPassword();
//        User user = userService.userLogin(userAccount, userPassword, request);
//        return user;
//    }

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User currentUser = userService.getLoginUserByToken(request);
        if (currentUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        // TODO 校验用户是否合法
        User user = userService.getUserById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    @GetMapping("user-info/{id}")
    public BaseResponse<User> getUserInfo(@PathVariable("id") long userId) {
        if (userId <= 0) {
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        }
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }
}
