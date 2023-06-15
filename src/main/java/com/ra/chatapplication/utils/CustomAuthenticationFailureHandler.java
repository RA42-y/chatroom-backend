package com.ra.chatapplication.utils;


import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CustomAuthenticationFailureHandler is responsible for handling failed authentication attempts.
 * It increments user's failure login attempts counter, blocks user's account after more than 3 failure and redirects user to the appropriate page.
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("email");
        System.out.println(email + " failed.");
        User user = userService.getUserByEmail(email);
        if (user != null) {
            if (!user.isActive()){
                super.setDefaultFailureUrl("/login?blocked");
                super.onAuthenticationFailure(request, response, exception);
            } else {
                int failureTimes = user.getFailureTimes() + 1;
                user.setFailureTimes(failureTimes);
                userService.saveUser(user);
                System.out.println(email + " failed attempts: " + failureTimes);
                if (failureTimes > 3) {
                    if (user.isActive()) {
                        userService.deactivateUser(user);
                    }
                    System.out.println("Account blacked after " + failureTimes + " attempts failed.");
                    super.setDefaultFailureUrl("/login?blocked");
                    super.onAuthenticationFailure(request, response, exception);
                } else {
                    super.setDefaultFailureUrl("/login?error");
                    super.onAuthenticationFailure(request, response, exception);
                }
            }
        } else {
            super.setDefaultFailureUrl("/login?error");
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}

