package com.ra.chatapplication.controller;


import com.ra.chatapplication.common.BaseResponse;
import com.ra.chatapplication.common.ErrorCode;
import com.ra.chatapplication.common.ResultUtils;
import com.ra.chatapplication.exception.CustomException;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.request.ChatCreateRequest;
import com.ra.chatapplication.model.request.ChatJoinRequest;
import com.ra.chatapplication.service.UserService;
import com.ra.chatapplication.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * URL de base du endpoint : http://localhost:8080/admin<br>
 * ex users : http://localhost:8080/admin/users
 */
@RestController
@RequestMapping("/chat")
//@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
@Slf4j
public class ChatController {

    @Autowired
    private HttpSession httpSession;

    @Resource
    UserService userService;

    @Resource
    ChatService chatService;

//    @GetMapping("chat-created-list/{id}")
//    public List<Chat> getChatsCreateByUser(@PathVariable("id") long userId) {
//        User user = userService.getUserById(userId);
//        List<Chat> chats = chatService.getChatsCreatedByUser(user);
//        return chats;
//    }

//    @GetMapping("chat-created-list")
//    public BaseResponse<List<Chat>> getChatsCreateByUser(HttpServletRequest request) {
//        User loginUser = userService.getLoginUser(request);
//        if (loginUser == null) {
//            throw new CustomException(ErrorCode.NOT_LOGIN);
//        }
////        User user = userService.getUserById(userId);
//        List<Chat> chats = chatService.getChatsCreatedByUser(loginUser);
//        return ResultUtils.success(chats);
////        return chats;
//    }

    @GetMapping("chat-list")
    public BaseResponse<Page<Chat>> getChatList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size, HttpServletRequest request) {
        Page<Chat> chats = chatService.getAllChatsByPage(page, size);
        return ResultUtils.success(chats);
    }

    @GetMapping("chat-created-list")
    public BaseResponse<Page<Chat>> getChatsCreateByUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Page<Chat> chats = chatService.getChatsCreatedByUserByPage(loginUser, page, size);
        return ResultUtils.success(chats);
    }

    @GetMapping("chat-joined-list")
    public BaseResponse<Page<Chat>> getChatsJoinedByUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Page<Chat> chats = chatService.getChatsJoinedByUserByPage(loginUser, page, size);
        return ResultUtils.success(chats);
    }

    @PostMapping("join")
    public BaseResponse<Boolean> joinTeam(@RequestBody ChatJoinRequest chatJoinRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        boolean result = chatService.joinChat(chatJoinRequest, loginUser);
        return ResultUtils.success(result);
    }


    @PostMapping("create")
    public BaseResponse<Chat> createChat(@RequestBody ChatCreateRequest chatCreateRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Chat chat = chatService.createChat(chatCreateRequest.getName(), chatCreateRequest.getDescription(), loginUser);
        return ResultUtils.success(chat);
//        return chat;
    }


//    @GetMapping("user-list")
//    public String getUserList(Model model) {
//        List<User> users = userService.getAllUsers();
//        model.addAttribute("users", users);
//        return "admin/user-list";
//    }

}
