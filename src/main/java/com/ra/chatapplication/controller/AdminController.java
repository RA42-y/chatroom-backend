package com.ra.chatapplication.controller;


import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.model.request.AdminCreateUserRequest;
import com.ra.chatapplication.model.request.AdminEditUserRequest;
import com.ra.chatapplication.service.ChatService;
import com.ra.chatapplication.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.ra.chatapplication.constant.UserConstant.USER_LOGIN_STATE;

/**
 * URL de base du endpoint : http://localhost:8080/admin<br>
 * ex users : http://localhost:8080/admin/users
 */
@Controller
@RequestMapping("admin")
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
public class AdminController {
    @Resource
    UserService userService;

    @Resource
    ChatService chatService;

//    @GetMapping("user-list")
//    public String getUserList(Model model) {
//        List<User> users = userService.getAllUsers();
//        model.addAttribute("users", users);
//        return "admin/user-list";
//    }

    @GetMapping("user-list")
    public String getUserList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size, @RequestParam(defaultValue = "") String sortBy, @RequestParam(defaultValue = "") String keyword, Model model, HttpSession session) {
        if (keyword.equals("")) {
            Page<User> users = userService.getAllUsersByPage(page, size, sortBy);
            model.addAttribute("users", users);
        } else {
            Page<User> searchResults = userService.searchUsers(keyword, page, size, sortBy);
            model.addAttribute("users", searchResults);
        }
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("keyword", keyword);
//        System.out.println(session.getAttribute(USER_LOGIN_STATE));
        return "admin/user-list";
    }

//    @GetMapping("deactivated-user-list")
//    public String getDeactivatedUserList(Model model) {
//        List<User> users = userService.getAllDeactivatedUsers();
//        model.addAttribute("users", users);
//        return "admin/deactivated-user-list";
//    }

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

    @GetMapping("create-user")
    public String getCreateUser(Model model) {
        model.addAttribute("adminCreateUserRequest", new AdminCreateUserRequest());
        return "admin/create-user";
    }

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

    @GetMapping("delete-user/{id}")
    public String getDeleteUser(@PathVariable("id") long userId, RedirectAttributes ra) {
        User user = userService.getUserById(userId);
        if (user != null) {
            List<Chat> chatsCreated = chatService.getChatsCreatedByUser(user);
            for (Chat c : chatsCreated) {
                chatService.removeCreator(c);
                chatService.saveChat(c);
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

    @GetMapping("edit-user/{id}")
    public String getEditUser(@PathVariable("id") long userId, Model model) {
        User user = userService.getUserById(userId);
        AdminEditUserRequest adminEditUserRequest = new AdminEditUserRequest(user.getEmail(), user.getFirstName(), user.getLastName(), user.getAdmin());
        model.addAttribute("adminEditUserRequest", adminEditUserRequest);
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

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
}
