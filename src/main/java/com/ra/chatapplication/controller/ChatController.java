package com.ra.chatapplication.controller;


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
//@CrossOrigin(origins = {"http://localhost:5173"}, allowCredentials = "true")
@Slf4j
public class ChatController {

    @Autowired
    private HttpSession httpSession;

    @Resource
    UserService userService;

    @Resource
    ChatService chatService;

//    @GetMapping("chat-created-list/{id}")
//    public List<Chat> getChatsCreateByUser(@PathVariable("id") long userID) {
//        User user = userService.getUserById(userID);
//        List<Chat> chats = chatService.getChatsCreatedByUser(user);
//        return chats;
//    }

    @GetMapping("chat-created-list")
    public List<Chat> getChatsCreateByUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
//        User user = userService.getUserById(userID);
        List<Chat> chats = chatService.getChatsCreatedByUser(loginUser);
        return chats;
    }

    @GetMapping("chat-joined-list")
    public List<Chat> getChatsJoinedByUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
//        User user = userService.getUserById(userID);
        List<Chat> chats = chatService.getChatsOfUser(loginUser);
        return chats;
    }


    @GetMapping("join")
    public Boolean joinTeam(@RequestBody ChatJoinRequest chatJoinRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return chatService.joinChat(chatJoinRequest, loginUser);
    }


    @PostMapping("create")
    public Long createChat(@RequestBody ChatCreateRequest chatCreateRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        Chat chat = chatService.createChat(chatCreateRequest.getName(), chatCreateRequest.getDescription(), loginUser);
        return chat.getId();
    }



//    @GetMapping("user-list")
//    public String getUserList(Model model) {
//        List<User> users = userService.getAllUsers();
//        model.addAttribute("users", users);
//        return "admin/user-list";
//    }

}
