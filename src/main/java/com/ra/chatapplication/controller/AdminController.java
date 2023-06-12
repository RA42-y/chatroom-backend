package com.ra.chatapplication.controller;


import com.ra.chatapplication.common.ErrorCode;
import com.ra.chatapplication.exception.CustomException;
import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.model.request.AdminCreateUserRequest;
import com.ra.chatapplication.model.request.AdminEditUserRequest;
import com.ra.chatapplication.service.ChatService;
import com.ra.chatapplication.service.UserService;
import com.ra.chatapplication.utils.JwtUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The AdminController class handles admin-related operations.
 * Base URL of the endpoint: http://localhost:8080/admin
 */
@Controller
@RequestMapping("admin")
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
public class AdminController {
    @Resource
    UserService userService;

    @Resource
    ChatService chatService;

    @Resource
    private JwtUtils jwtUtils;

    /**
     * Retrieves a paginated list of all users for display in the user list page.
     *
     * @param page    The page number (default: 0)
     * @param size    The number of users per page (default: 7)
     * @param sortBy  The field to sort the users by (default: "")
     * @param keyword The keyword for searching users by name or email (default: "")
     * @param model   The model to hold the user list and other attributes
     * @return The view name for displaying the user list page
     */
    @GetMapping("user-list")
    public String getUserList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size, @RequestParam(defaultValue = "") String sortBy, @RequestParam(defaultValue = "") String keyword, Model model) {
        if (keyword.equals("")) {
            Page<User> users = userService.getAllUsersByPage(page, size, sortBy);
            model.addAttribute("users", users);
        } else {
            Page<User> searchResults = userService.searchUsers(keyword, page, size, sortBy);
            model.addAttribute("users", searchResults);
        }
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("keyword", keyword);
        return "admin/user-list";
    }

    /**
     * Retrieves a paginated list of all deactivated users for display in the deactivated user list page.
     *
     * @param page    The page number (default: 0)
     * @param size    The number of users per page (default: 7)
     * @param sortBy  The field to sort the users by (default: "")
     * @param keyword The keyword for searching users by name or email (default: "")
     * @param model   The model to hold the deactivated user list and other attributes
     * @return The view name for displaying the deactivated user list page
     */
    @GetMapping("deactivated-user-list")
    public String getDeactivatedUserList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size, @RequestParam(defaultValue = "") String sortBy, @RequestParam(defaultValue = "") String keyword, Model model) {
        if (keyword.equals("")) {
            Page<User> users = userService.getAllDeactivatedUsersByPage(page, size, sortBy);
            model.addAttribute("users", users);
        } else {
            Page<User> searchResults = userService.searchDeactivatedUsers(keyword, page, size, sortBy);
            model.addAttribute("users", searchResults);
        }
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("keyword", keyword);
        return "admin/deactivated-user-list";
    }

    /**
     * Displays the form for creating a new user by the admin.
     *
     * @param model The model to hold the form data
     * @return The view name for displaying the create user page
     */
    @GetMapping("create-user")
    public String getCreateUser(Model model) {
        model.addAttribute("adminCreateUserRequest", new AdminCreateUserRequest());
        return "admin/create-user";
    }

    /**
     * Handles the submission of the create user form by the admin.
     *
     * @param adminCreateUserRequest The create user request data
     * @param model                  The model to hold the result data
     * @return The view name for displaying the create user page with the result message
     * @throws MessagingException if there is an error sending the email notification
     */
    @PostMapping("create-user")
    public String postCreateUser(@ModelAttribute AdminCreateUserRequest adminCreateUserRequest, Model model) throws MessagingException {
        String email = adminCreateUserRequest.getEmail();
        String firstName = adminCreateUserRequest.getFirstName();
        String lastName = adminCreateUserRequest.getLastName();
        Boolean admin = adminCreateUserRequest.getAdmin();

        if (userService.getUserByEmail(email) != null) {
            model.addAttribute("createUserSuccess", false);
            model.addAttribute("duplicatedEmail", email);
        } else {
            User newUser = userService.createUserByAdmin(firstName, lastName, email, admin);
            model.addAttribute("createUserSuccess", true);
            model.addAttribute("newUser", newUser);
        }
        return "admin/create-user";
    }

    /**
     * Deactivates a user with the specified ID.
     *
     * @param userId The ID of the user to deactivate
     * @param ra     The RedirectAttributes object to hold the result data
     * @return The redirection URL to the user list page
     */
    @GetMapping("deactivate-user/{id}")
    public String getDeactivateUser(@PathVariable("id") long userId, RedirectAttributes ra) {
        User user = userService.getUserById(userId);
        if (user != null) {
            userService.deactivateUser(user);
            ra.addFlashAttribute("deactivateUserSuccess", true);
            ra.addFlashAttribute("deactivatedUser", user);
        } else {
            ra.addFlashAttribute("deactivateUserSuccess", false);
            ra.addFlashAttribute("failedUserId", userId);
        }
        return "redirect:/admin/user-list";
    }

    /**
     * Activates a user with the specified ID.
     *
     * @param userId The ID of the user to activate
     * @param ra     The RedirectAttributes object to hold the result data
     * @return The redirection URL to the user list page
     */
    @GetMapping("activate-user/{id}")
    public String getActivateUser(@PathVariable("id") long userId, RedirectAttributes ra) {
        User user = userService.getUserById(userId);
        if (user != null) {
            userService.activateUser(user);
            userService.resetUserFailureTimes(user);
            ra.addFlashAttribute("activateUserSuccess", true);
            ra.addFlashAttribute("activatedUser", user);
        } else {
            ra.addFlashAttribute("activateUserSuccess", false);
            ra.addFlashAttribute("failedUserId", userId);
        }

        return "redirect:/admin/user-list";
    }

    /**
     * Deletes a user with the specified ID.
     *
     * @param userId The ID of the user to delete
     * @param ra     The RedirectAttributes object to hold the result data
     * @return The redirection URL to the user list page
     */
    @GetMapping("delete-user/{id}")
    public String getDeleteUser(@PathVariable("id") long userId, RedirectAttributes ra) {
        User user = userService.getUserById(userId);
        if (user != null) {
            List<Chat> chatsCreated = chatService.getChatsCreatedByUser(user);
            for (Chat c : chatsCreated) {
                chatService.removeAllUsersFromChat(c);
                chatService.removeCreator(c);
                chatService.saveChat(c);
                chatService.deleteChat(c);
            }
            List<Chat> chatsJoined = chatService.getChatsJoinedByUser(user);
            for (Chat c : chatsJoined) {
                chatService.removeUserFromChat(c, user);
                chatService.saveChat(c);
            }
            userService.deleteUserById(userId);
            ra.addFlashAttribute("deleteUserSuccess", true);
            ra.addFlashAttribute("deletedUserId", userId);
        } else {
            ra.addFlashAttribute("deleteUserSuccess", false);
            ra.addFlashAttribute("failedUserId", userId);
        }

        return "redirect:/admin/user-list";
    }

    /**
     * Displays the form for editing a user's details.
     *
     * @param userId The ID of the user to edit
     * @param model  The model to hold the form data and user details
     * @return The view name for displaying the edit user page
     */
    @GetMapping("edit-user/{id}")
    public String getEditUser(@PathVariable("id") long userId, Model model) {
        User user = userService.getUserById(userId);
        AdminEditUserRequest adminEditUserRequest = new AdminEditUserRequest(user.getEmail(), user.getFirstName(), user.getLastName(), user.getAdmin());
        model.addAttribute("adminEditUserRequest", adminEditUserRequest);
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    /**
     * Handles the submission of the user edit form.
     *
     * @param userId               The ID of the user to edit
     * @param adminEditUserRequest The request object containing the updated user details
     * @param ra                   The redirect attributes to hold the result data
     * @return The redirection URL to the user list page
     */
    @PostMapping("edit-user/{id}")
    public String postEditUser(@PathVariable("id") long userId, @ModelAttribute AdminEditUserRequest adminEditUserRequest, RedirectAttributes ra) {
        User user = userService.getUserById(userId);
        if (user != null) {
            userService.editUser(user, adminEditUserRequest.getFirstName(), adminEditUserRequest.getLastName(), adminEditUserRequest.getAdmin());
            ra.addFlashAttribute("editUserSuccess", true);
            ra.addFlashAttribute("editedUser", user);
        } else {
            ra.addFlashAttribute("editUserSuccess", false);
            ra.addFlashAttribute("failedUserId", userId);
        }
        return "redirect:/admin/user-list";
    }

    /**
     * Redirect admin user from admin page to Chatroom page.
     *
     * @param request  The HttpServletRequest object containing the user's request
     * @param response The HttpServletResponse object to redirect user
     */
    @GetMapping("to-chatroom")
    public void getToChatroom(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = userService.getLoginUser(request);
        if (user == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        } else {
            String token = jwtUtils.generateJwtToken();
            response.sendRedirect("http://localhost:3000/chats?token=" + token);
        }
    }

    /**
     * Redirect admin user from admin page to API documentation page.
     *
     * @param request  The HttpServletRequest object containing the user's request
     * @param response The HttpServletResponse object to redirect user
     */
    @GetMapping("to-api-documentation")
    public void getToApiDocumentation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = userService.getLoginUser(request);
        if (user == null) {
            throw new CustomException(ErrorCode.NOT_LOGIN);
        } else {
            response.sendRedirect("http://localhost:8080/doc.html#/home");
        }
    }
}
