package com.ra.chatapplication.controller;


import com.ra.chatapplication.common.BaseResponse;
import com.ra.chatapplication.common.ErrorCode;
import com.ra.chatapplication.common.ResultUtils;
import com.ra.chatapplication.exception.CustomException;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The UserController class handles the HTTP requests related to user.
 * Base URL of the endpoint: http://localhost:8080/user
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
@Slf4j
public class UserController {
    @Resource
    UserService userService;

    /**
     * Retrieves a list of all users.
     *
     * @return A BaseResponse containing the list of users
     */
    @GetMapping("all-user-list")
    public BaseResponse<List<User>> getUserList() {
        List<User> users = userService.getAllUsers();
        return ResultUtils.success(users);
    }

    /**
     * Retrieves the information of the logged-in user
     *
     * @param request The HttpServletRequest object containing the user's request
     * @return A BaseResponse containing the user information
     * @throws CustomException If the user is not logged in
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User currentUser = userService.getLoginUserByToken(request);
        if (currentUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        User user = userService.getUserById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    /**
     * Retrieves the information of a user with the specified ID.
     *
     * @param userId The ID of the user
     * @return A BaseResponse containing the user information
     * @throws CustomException If the ID is invalid, the user is not found
     */
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
