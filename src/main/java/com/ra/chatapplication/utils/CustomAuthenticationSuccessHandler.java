package com.ra.chatapplication.utils;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import static com.ra.chatapplication.constant.UserConstant.USER_LOGIN_STATE;

/**
 * CustomAuthenticationSuccessHandler is responsible for handling successful authentication.
 * It updates user data, resets user's failure login attempts counter and redirects user to the appropriate page according to their role.
 */
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Authentication loginUser = SecurityContextHolder.getContext().getAuthentication();
        String email = loginUser.getName();
        User user = userService.getUserByEmail(email);
        System.out.println(user.getEmail() + " success.");
        if (user.getFailureTimes() > 0) {
            user.setFailureTimes(0);
            userService.saveUser(user);
        }

        User safetyUser = userService.getSafetyUser(user);
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (user.isFirstLogin()) {
            response.sendRedirect("/login/reset-password");
        } else {
            if (isAdmin) {
                response.sendRedirect("/admin/user-list");
            } else {
                String token = jwtUtils.generateJwtToken();
                response.sendRedirect("http://localhost:3000/chats?token=" + token);
            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
