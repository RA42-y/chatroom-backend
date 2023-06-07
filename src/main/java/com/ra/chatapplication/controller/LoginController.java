package com.ra.chatapplication.controller;


import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.model.request.UserLoginRequest;
import com.ra.chatapplication.model.request.UserResetPasswordRequest;
import com.ra.chatapplication.service.UserService;
import com.ra.chatapplication.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The LoginController class handles the requests related to user login and authentication.
 * Base URL of the endpoint: http://localhost:8080/login
 */
@Controller
@RequestMapping(value = {"login"})
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
public class LoginController {
    @Resource
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Displays the form for logging in.
     *
     * @param model The model to hold the login form
     * @return The view name for login page
     */
    @GetMapping("")
    public String getLogin(Model model) {
        model.addAttribute("userLoginRequest", new UserLoginRequest());
        return "login/login";
    }

    /**
     * Check the user's role and login status and redirect user to correspond page.
     *
     * @param ra      The RedirectAttributes object for adding flash attributes
     * @param session The HttpSession object containing the user's session
     * @param request The HttpServletRequest object containing the user's request
     * @return The redirect URL based on the user's role and login status
     */
    @GetMapping("check")
    public String getCheckLogin(RedirectAttributes ra, HttpSession session, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(loginUser.getEmail());
        if (!loginUser.isActive()) {
            session.invalidate();
            return "redirect:/login?blocked";
        }
        if (loginUser.isFirstLogin()) {
            ra.addFlashAttribute("user", loginUser);
            return "redirect:/login/reset-password";
        }
        if (loginUser.isAdmin()) {
            return "redirect:/admin/user-list";
        } else {
            String token = jwtUtils.generateJwtToken();
            return "redirect:http://localhost:3000/chats?token=" + token;
        }
    }

    /**
     * Displays the form for resetting password.
     *
     * @param model   The model to hold the login form
     * @param request The HttpServletRequest object containing the user's request
     * @return The view name for resetting password.
     */
    @GetMapping("reset-password")
    public String getResetPassword(Model model, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        System.out.println(loginUser.getEmail());
        if (loginUser.isFirstLogin()) {
            model.addAttribute("firstLogin", true);
        }
        model.addAttribute("userResetPasswordRequest", new UserResetPasswordRequest());
        return "login/reset-password";
    }

    /**
     * Retrieves the raw password of a user by their ID.
     *
     * @param userId The ID of the user
     * @return The raw password of the user
     */
    private String getUserRawPassword(long userId) {
        return userService.getUserById(userId).getPassword();
    }

    /**
     * Handles the submission of the reset password form.
     *
     * @param userResetPasswordRequest the UserResetPasswordRequest object containing the new password
     * @param request                  The HttpServletRequest object containing the user's request
     * @param model                    The model to hold the result data
     * @return The redirect URL based on the user's role and login status
     */
    @PostMapping("reset-password")
    public String postResetPassword(@ModelAttribute UserResetPasswordRequest userResetPasswordRequest, HttpServletRequest request, Model model) {
        User loginUser = userService.getLoginUser(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userResetPasswordRequest.getPasswordNew().equals(userResetPasswordRequest.getPasswordValidation())) {
            if (userService.comparePasswords(userResetPasswordRequest.getPasswordNew(), getUserRawPassword(loginUser.getId()))) {
                model.addAttribute("invalidNotChanged", true);
                return "login/reset-password";
            }
            loginUser.setPassword(passwordEncoder.encode(userResetPasswordRequest.getPasswordNew()));
            loginUser.setFirstLogin(false);
            userService.saveUser(loginUser);
            if (loginUser.isAdmin()) {
                return "redirect:/admin/user-list";
            } else {
                String token = jwtUtils.generateJwtToken();
                return "redirect:http://localhost:3000/chats?token=" + token;
            }
        } else {
            model.addAttribute("invalid", true);
            return "login/reset-password";
        }
    }

    /**
     * Handles request to log out and invalidates the user's session.
     *
     * @param session The HttpSession object containing the user's session
     * @return the redirect URL to the login page after logged out
     */
    @GetMapping("/logout")
    public String getLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }

}
