package com.ra.chatapplication.controller;


import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.model.request.UserLoginRequest;
import com.ra.chatapplication.model.request.UserResetPasswordRequest;
import com.ra.chatapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.ra.chatapplication.constant.UserConstant.USER_LOGIN_STATE;

/**
 * URL de base du endpoint : http://localhost:8080/admin<br>
 * ex users : http://localhost:8080/admin/users
 */
@Controller
@RequestMapping(value = {"login"})
public class LoginController {
    @Resource
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("")
    public String getLogin(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Model model) {
        model.addAttribute("userLoginRequest", new UserLoginRequest());
//        if (error != null) {
//            // Handle the error
//            model.addAttribute("errorMessage", "Invalid credentials. Please try again.");
//        }
//        if (logout != null) {
//            model.addAttribute("logoutMessage", "You have successfully logged out.");
//        }
        return "login/login";
    }

//    @PostMapping("check")
//    public String postCheckLogin(@ModelAttribute UserLoginRequest userLoginRequest, Model model, RedirectAttributes ra, HttpSession session, HttpServletRequest request) {
//
//        System.out.println(userLoginRequest.getEmail());
//        System.out.println(userLoginRequest.getPassword());
//
//        User user = userService.userLogin(userLoginRequest.getEmail(), userLoginRequest.getPassword(), request);
//
//        if (user != null && user.isAdmin()) {
////            session.setAttribute("loginAdminEmail", user.getEmail());
////            session.setAttribute("loginAdminFirstName", user.getFirstName());
////            session.setAttribute("loginAdminLastName", user.getLastName());
//            if (user.isFirstLogin()) {
//                ra.addFlashAttribute("user", user);
//                return "redirect:/login/reset-password";
//            }
//            return "redirect:/admin/user-list";
//        } else {
//            model.addAttribute("invalid", true);
//            return "login/login";
//        }
//    }

    @GetMapping("check")
    public String postCheckLogin(RedirectAttributes ra, HttpSession session, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        System.out.println(loginUser.getEmail());

        if (loginUser.isFirstLogin()){
            ra.addFlashAttribute("user", loginUser);
            return "redirect:/login/reset-password";
        }
        if (loginUser.isAdmin()) {
            return "redirect:/admin/user-list";
        } else {
            return "redirect:/chat"; // to change
        }
    }

    @GetMapping("reset-password")
    public String getResetPassword(Model model, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        System.out.println(loginUser.getEmail());
        if (loginUser.isFirstLogin()) {
            model.addAttribute("firstLogin", true);
        }
        model.addAttribute("userResetPasswordRequest", new UserResetPasswordRequest(loginUser.getEmail()));
        return "login/reset-password";
    }

    @PostMapping("reset-password")
    public String postResetPassword(@ModelAttribute UserResetPasswordRequest userResetPasswordRequest, HttpServletRequest request, Model model) {
        User loginUser = userService.getLoginUser(request);
        if (userResetPasswordRequest.getPasswordNew().equals(userResetPasswordRequest.getPasswordValidation())) {
            if (userService.comparePasswords(userResetPasswordRequest.getPasswordNew(), loginUser.getPassword())) {
                model.addAttribute("invalidNotChanged", true);
                return "login/reset-password";
            }
            loginUser.setPassword(passwordEncoder.encode(userResetPasswordRequest.getPasswordNew()));
            loginUser.setFirstLogin(false);
            userService.saveUser(loginUser);
            if (loginUser.isAdmin()){
                return "redirect:/admin/user-list";
            } else {
                return "redirect:/chat"; // to change
            }
        } else {
            model.addAttribute("invalid", true);
            return "login/reset-password";
        }
    }

//    @GetMapping("/logout")
//    public String getLogout(HttpSession session) {
//        session.invalidate();
//        return "redirect:/login";
//    }

}
