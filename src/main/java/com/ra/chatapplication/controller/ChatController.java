package com.ra.chatapplication.controller;


import com.ra.chatapplication.common.BaseResponse;
import com.ra.chatapplication.common.ErrorCode;
import com.ra.chatapplication.common.ResultUtils;
import com.ra.chatapplication.exception.CustomException;
import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.model.request.ChatCreateRequest;
import com.ra.chatapplication.model.request.ChatEditRequest;
import com.ra.chatapplication.model.request.InviteUserRequest;
import com.ra.chatapplication.model.request.RemoveUserRequest;
import com.ra.chatapplication.service.ChatService;
import com.ra.chatapplication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
//        User loginUser = userService.getLoginUserByToken(request);
//        if (loginUser == null) {
//            throw new CustomException(ErrorCode.NOT_LOGIN);
//        }
////        User user = userService.getUserById(userId);
//        List<Chat> chats = chatService.getChatsCreatedByUser(loginUser);
//        return ResultUtils.success(chats);
////        return chats;
//    }

    @GetMapping("all-chat-list")
    public BaseResponse<Page<Chat>> getAllChatList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, HttpServletRequest request) {
        Page<Chat> chats = chatService.getAllChatsByPage(page, size);
        return ResultUtils.success(chats);
    }

    @GetMapping("my-chat-list")
    public BaseResponse<Page<Chat>> getChatsOfUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, HttpServletRequest request) {
        User loginUser = userService.getLoginUserByToken(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Page<Chat> chats = chatService.getChatsOfUserByPage(loginUser, page, size);
        return ResultUtils.success(chats);
    }

    @GetMapping("created-chat-list")
    public BaseResponse<Page<Chat>> getChatsCreateByUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, HttpServletRequest request) {
        User loginUser = userService.getLoginUserByToken(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Page<Chat> chats = chatService.getChatsCreatedByUserByPage(loginUser, page, size);
        return ResultUtils.success(chats);
    }

    @GetMapping("joined-chat-list")
    public BaseResponse<Page<Chat>> getChatsJoinedByUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, HttpServletRequest request) {
        User loginUser = userService.getLoginUserByToken(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Page<Chat> chats = chatService.getChatsJoinedByUserByPage(loginUser, page, size);
        return ResultUtils.success(chats);
    }

    @GetMapping("chat-info/{id}")
    public BaseResponse<Chat> getChatInfo(@PathVariable("id") long chatId) {
        Chat chat = chatService.getChatById(chatId);
        return ResultUtils.success(chat);
    }

    @GetMapping("member-list/{id}")
    public BaseResponse<List<User>> getMemberList(@PathVariable("id") long chatId) {
        Chat chat = chatService.getChatById(chatId);
        if (chat == null) {
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(chat.getMembers());
    }

    @PostMapping("create-chat")
    public BaseResponse<Chat> createChat(@RequestBody ChatCreateRequest chatCreateRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUserByToken(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Chat chat = chatService.createChat(chatCreateRequest.getName(), chatCreateRequest.getDescription(), loginUser);
        return ResultUtils.success(chat);
    }

    @DeleteMapping("delete-chat/{id}")
    public BaseResponse<Boolean> deleteChat(@PathVariable("id") long chatId, HttpServletRequest request) {
        User loginUser = userService.getLoginUserByToken(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Chat chat = chatService.getChatById(chatId);
        if (chat == null) {
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        }
        if (chatService.isUserCreator(chat, loginUser)) {
            chatService.deleteChat(chat);
        } else {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        return ResultUtils.success(true);
    }

    @PostMapping("edit-chat/{id}")
    public BaseResponse<Chat> editChat(@PathVariable("id") long chatId, @RequestBody ChatEditRequest chatEditRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUserByToken(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Chat chat = chatService.getChatById(chatId);
        if (chat == null) {
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        }
        if (chatEditRequest == null) {
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        }
        if (chatService.isUserCreator(chat, loginUser)) {
            chatService.editChat(chat, chatEditRequest.getName(), chatEditRequest.getDescription());
            chatService.saveChat(chat);
        } else {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        return ResultUtils.success(chat);
    }

    @GetMapping("quit-chat/{id}")
    public BaseResponse<Boolean> quitChat(@PathVariable("id") long chatId, HttpServletRequest request) {
        User loginUser = userService.getLoginUserByToken(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Chat chat = chatService.getChatById(chatId);
        if (chat == null) {
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        }
        if (chatService.isUserMember(chat, loginUser)) {
            chatService.removeUserFromChat(chat, loginUser);
            chatService.saveChat(chat);
        } else {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        return ResultUtils.success(true);
    }

    @PutMapping("invite-user/{id}")
    public BaseResponse<Chat> inviteUserToChat(@PathVariable("id") long chatId, @RequestBody InviteUserRequest inviteUserRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUserByToken(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Chat chat = chatService.getChatById(chatId);
        if (chat == null) {
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        }
        if (inviteUserRequest == null) {
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        }
        User newMember = userService.getUserById(inviteUserRequest.getUserId());
        if (newMember == null) {
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        }
        if (chatService.isUserCreator(chat, loginUser)) {
            chatService.addMemberToChat(chat, newMember);
            chatService.saveChat(chat);
        } else {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        return ResultUtils.success(chat);
    }

    @PutMapping("remove-user/{id}")
    public BaseResponse<Chat> removeUserFromChat(@PathVariable("id") long chatId, @RequestBody RemoveUserRequest removeUserRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUserByToken(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Chat chat = chatService.getChatById(chatId);
        if (chat == null) {
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        }
        if (removeUserRequest == null) {
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        }
        User member = userService.getUserById(removeUserRequest.getUserId());
        if (member == null || !chatService.isUserMember(chat, member)) {
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        }
        if (chatService.isUserCreator(chat, loginUser)) {
            chatService.removeUserFromChat(chat, member);
            chatService.saveChat(chat);
        } else {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        return ResultUtils.success(chat);
    }

}
