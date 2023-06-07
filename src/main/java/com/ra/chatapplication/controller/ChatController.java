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
 * The ChatController class handles the HTTP requests related to chats.
 * Base URL of the endpoint: http://localhost:8080/chat
 */
@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
@Slf4j
public class ChatController {

    @Resource
    UserService userService;

    @Resource
    ChatService chatService;

    /**
     * Retrieves a paginated list of all chats.
     *
     * @param page The page number (default: 0)
     * @param size The number of chats per page (default: 6)
     * @return A BaseResponse containing the paginated list of chats
     */
    @GetMapping("all-chat-list")
    public BaseResponse<Page<Chat>> getAllChatList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        Page<Chat> chats = chatService.getAllChatsByPage(page, size);
        return ResultUtils.success(chats);
    }

    /**
     * Retrieves a paginated list of chats for the logged-in user.
     *
     * @param page    The page number (default: 0)
     * @param size    The number of chats per page (default: 6)
     * @param request The HttpServletRequest object containing the user's request
     * @return A BaseResponse containing the paginated list of chats
     * @throws CustomException If the user is not logged in
     */
    @GetMapping("my-chat-list")
    public BaseResponse<Page<Chat>> getChatsOfUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size, HttpServletRequest request) {
        User loginUser = userService.getLoginUserByToken(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Page<Chat> chats = chatService.getChatsOfUserByPage(loginUser, page, size);
        return ResultUtils.success(chats);
    }

    /**
     * Retrieves a paginated list of chats created by the logged-in user.
     *
     * @param page    The page number (default: 0)
     * @param size    The number of chats per page (default: 6)
     * @param request The HttpServletRequest object containing the user's request
     * @return A BaseResponse containing the paginated list of chats
     * @throws CustomException If the user is not logged in
     */
    @GetMapping("created-chat-list")
    public BaseResponse<Page<Chat>> getChatsCreateByUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size, HttpServletRequest request) {
        User loginUser = userService.getLoginUserByToken(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Page<Chat> chats = chatService.getChatsCreatedByUserByPage(loginUser, page, size);
        return ResultUtils.success(chats);
    }

    /**
     * Retrieves a paginated list of chats joined by the logged-in user.
     *
     * @param page    The page number (default: 0)
     * @param size    The number of chats per page (default: 6)
     * @param request The HttpServletRequest object containing the user's request
     * @return A BaseResponse containing the paginated list of chats
     * @throws CustomException If the user is not logged in
     */
    @GetMapping("joined-chat-list")
    public BaseResponse<Page<Chat>> getChatsJoinedByUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size, HttpServletRequest request) {
        User loginUser = userService.getLoginUserByToken(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Page<Chat> chats = chatService.getChatsJoinedByUserByPage(loginUser, page, size);
        return ResultUtils.success(chats);
    }

    /**
     * Retrieves the information of a chat with the specified ID.
     *
     * @param chatId The ID of the chat
     * @return A BaseResponse containing the chat information
     */
    @GetMapping("chat-info/{id}")
    public BaseResponse<Chat> getChatInfo(@PathVariable("id") long chatId) {
        Chat chat = chatService.getChatById(chatId);
        return ResultUtils.success(chat);
    }

    /**
     * Retrieves the member list of a chat with the specified ID.
     *
     * @param chatId The ID of the chat
     * @return A BaseResponse containing the member list of the chat
     * @throws CustomException If the chat is not found
     */
    @GetMapping("member-list/{id}")
    public BaseResponse<List<User>> getMemberList(@PathVariable("id") long chatId) {
        Chat chat = chatService.getChatById(chatId);
        if (chat == null) {
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(chat.getMembers());
    }

    /**
     * Creates a new chat.
     *
     * @param chatCreateRequest The request object containing the chat details
     * @param request           The HttpServletRequest object containing the user's request
     * @return A BaseResponse containing the created chat
     * @throws CustomException If the user is not logged in
     */
    @PostMapping("create-chat")
    public BaseResponse<Chat> createChat(@RequestBody ChatCreateRequest chatCreateRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUserByToken(request);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        }
        Chat chat = chatService.createChat(chatCreateRequest.getName(), chatCreateRequest.getDescription(), loginUser);
        return ResultUtils.success(chat);
    }

    /**
     * Deletes a chat with the specified ID.
     *
     * @param chatId  The ID of the chat to delete
     * @param request The HttpServletRequest object containing the user's request
     * @return A BaseResponse indicating the success of the operation
     * @throws CustomException If the user is not logged in, the chat is not found, or the user is not the creator of the chat
     */
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

    /**
     * Edits a chat with the specified ID.
     *
     * @param chatId          The ID of the chat to edit
     * @param chatEditRequest The request object containing the updated chat details
     * @param request         The HttpServletRequest object containing the user's request
     * @return A BaseResponse containing the edited chat
     * @throws CustomException If the user is not logged in, the chat is not found, the request is null, or the user is not the creator of the chat
     */
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

    /**
     * Removes the logged-in user from a chat with the specified ID.
     *
     * @param chatId  The ID of the chat to quit
     * @param request The HttpServletRequest object containing the user's request
     * @return A BaseResponse indicating the success of the operation
     * @throws CustomException If the user is not logged in, the chat is not found, or the user is not a member of the chat
     */
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

    /**
     * Invites a user to a chat with the specified ID.
     *
     * @param chatId            The ID of the chat
     * @param inviteUserRequest The request object containing the user to invite
     * @param request           The HttpServletRequest object containing the user's request
     * @return A BaseResponse containing the updated chat
     * @throws CustomException If the user is not logged in, the chat is not found, the request is null, the invited user is not found, or the user is not the creator of the chat
     */
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

    /**
     * Removes a user from a chat with the specified ID.
     *
     * @param chatId            The ID of the chat
     * @param removeUserRequest The request object containing the user to remove
     * @param request           The HttpServletRequest object containing the user's request
     * @return A BaseResponse containing the updated chat
     * @throws CustomException If the user is not logged in, the chat is not found, the request is null, the removed user is not found, or the user is not the creator of the chat
     */
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
