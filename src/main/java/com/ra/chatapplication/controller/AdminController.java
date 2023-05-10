package com.ra.chatapplication.controller;


import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * URL de base du endpoint : http://localhost:8080/admin<br>
 * ex users : http://localhost:8080/admin/users
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Resource
    UserService userService;

    @GetMapping("user-list")
    public String getUserList(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//
//        User currentUser = userService.findCurrentUser();
//        model.addAttribute("currentUser", currentUser);

        return "admin/user-list";
    }

    @GetMapping("deactivated-user-list")
    public String getDeactivatedUserList(Model model) {
        List<User> users = userService.getAllDeactivatedUsers();
        model.addAttribute("users", users);

//        User currentUser = userService.findCurrentUser();
//        model.addAttribute("currentUser", currentUser);

        return "admin/deactivated-user-list";
    }
}
