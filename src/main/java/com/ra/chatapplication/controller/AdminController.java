package com.ra.chatapplication.controller;


import com.ra.chatapplication.dao.UserRepository;
import com.ra.chatapplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * URL de base du endpoint : http://localhost:8080/admin<br>
 * ex users : http://localhost:8080/admin/users
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("user-list")
    public String getUserList(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "admin/user-list";
    }

    @GetMapping("deactivated-user-list")
    public String getDeactivatedUserList(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "admin/deactivated-user-list";
    }
}
