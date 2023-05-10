package com.ra.chatapplication.controller;


import com.ra.chatapplication.model.User;
import com.ra.chatapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * URL de base du endpoint : http://localhost:8080/admin<br>
 * ex users : http://localhost:8080/admin/users
 */
@Controller
@RequestMapping(value = {"/", "login"})
public class LoginController {
    @Resource
    UserService userService;

    @GetMapping
    public String getLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping
    public String postLogin(@ModelAttribute User user, Model model) {

        User loggedUser = userService.userLogin(user.getEmail(), user.getPassword());

        if (loggedUser != null && loggedUser.isAdmin()){
            return "redirect:/admin/user-list";
        }
        else{
            model.addAttribute("invalid", true);
            return "login";
        }
    }
}
