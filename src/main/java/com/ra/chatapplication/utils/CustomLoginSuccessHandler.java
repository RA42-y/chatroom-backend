package com.ra.chatapplication.utils;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println("succ");
        User user =  (User) authentication.getPrincipal();
        if (user.getFailureTimes() > 0) {
            user.setFailureTimes(0);
        }

        request.getSession().setAttribute("user", user);
//        request.getSession().setAttribute("email", user.getEmail());
//        request.getSession().setAttribute("isfirstlogin",user.isFirstLogin());
        response.sendRedirect("/login/check");

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
