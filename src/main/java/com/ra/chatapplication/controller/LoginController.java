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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
    public String postCheckLogin(@ModelAttribute UserLoginRequest userLoginRequest, Model model, RedirectAttributes ra, HttpSession session) {

        System.out.println(userLoginRequest.getEmail());
        System.out.println(userLoginRequest.getPassword());

        User user = userService.userLogin(userLoginRequest.getEmail(), userLoginRequest.getPassword());

        if (user != null && user.isAdmin()) {
            session.setAttribute("loginAdminEmail", user.getEmail());
            session.setAttribute("loginAdminFirstName", user.getFirstName());
            session.setAttribute("loginAdminLastName", user.getLastName());
            if (user.isFirstLogin()) {
                ra.addFlashAttribute("user", user);
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
        if (user.isFirstLogin()) {
            model.addAttribute("firstLogin", true);
        }
        model.addAttribute("userResetPasswordRequest", new UserResetPasswordRequest(user.getEmail()));
        return "login/reset-password";
    }

    @PostMapping("reset-password")
    public String postResetPassword(@ModelAttribute UserResetPasswordRequest userResetPasswordRequest, Model model) {
        if (userResetPasswordRequest.getPasswordNew().equals(userResetPasswordRequest.getPasswordValidation())) {
            User user = userService.findUserByEmail(userResetPasswordRequest.getEmail());
            user.setPassword(userResetPasswordRequest.getPasswordNew());
            user.setFirstLogin(false);
            userService.editUser(user);
            return "redirect:/admin/user-list";
        } else {
            model.addAttribute("invalid", true);
            return "login/reset-password";
        }
    }

    @GetMapping("/logout")
    public String getLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
