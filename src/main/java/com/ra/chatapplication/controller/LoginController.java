package com.ra.chatapplication.controller;


import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.model.request.UserLoginRequest;
import com.ra.chatapplication.model.request.UserResetPasswordRequest;
import com.ra.chatapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

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
        model.addAttribute("userLoginRequest", new UserLoginRequest());
        return "login/login";
    }

    @PostMapping("check")
    public String postCheckLogin(@ModelAttribute UserLoginRequest userLoginRequest, Model model) {

        System.out.println(userLoginRequest.getEmail());
        System.out.println(userLoginRequest.getPassword());

        User loggedUser = userService.userLogin(userLoginRequest.getEmail(), userLoginRequest.getPassword());

        System.out.println(loggedUser.getEmail());

        if (loggedUser != null && loggedUser.isAdmin()) {
            if (loggedUser.isFirstLogin()) {
                model.addAttribute("user", loggedUser);
                return "redirect:/login/reset-password";
            }

            return "redirect:/admin/user-list";
        } else {
            model.addAttribute("invalid", true);
            return "login/login";
        }
    }

    @GetMapping("reset-password")
    public String getResetPassword(@ModelAttribute User user, Model model) {
        System.out.println(user.getEmail());
        model.addAttribute("userResetPasswordRequest", new UserResetPasswordRequest());
        model.addAttribute("email", user.getEmail());
        return "login/reset-password";
    }

    @PostMapping("reset-password")
    public String postResetPassword(@ModelAttribute UserResetPasswordRequest userResetPasswordRequest, Model model) {
        System.out.println(userResetPasswordRequest.getEmail());
        System.out.println(userResetPasswordRequest.getPasswordNew());
        System.out.println(userResetPasswordRequest.getPasswordValidation());

        if (userResetPasswordRequest.getPasswordNew().equals(userResetPasswordRequest.getPasswordValidation())) {
            User user = userService.findUserByEmail(userResetPasswordRequest.getEmail());
            user.setPassword(userResetPasswordRequest.getPasswordNew());
            user.setFirstLogin(false);
            return "redirect:/admin/user-list";
        } else {
            model.addAttribute("invalid", true);
            return "login/reset-password";
        }

    }

}
