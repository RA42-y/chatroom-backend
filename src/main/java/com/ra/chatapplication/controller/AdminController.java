package com.ra.chatapplication.controller;


import com.ra.chatapplication.dao.UserRepository;
import com.ra.chatapplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Optional;

/**
 * URL de base du endpoint : http://localhost:8080/admin<br>
 * ex users : http://localhost:8080/admin/users
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("users")
    public String getUserList(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "user_list";
    }
}
