package com.ra.chatapplication;

import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.service.ChatService;
import com.ra.chatapplication.service.UserService;
import com.ra.chatapplication.utils.EmailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;

@SpringBootTest
public class UserTest {

    @Autowired
    private EmailUtils emailUtils;

    @Resource
    private TemplateEngine templateEngine;

    @Resource
    private UserService userService;

    @Resource
    private ChatService chatService;

    @Test
    public void getUserInfo() {

        System.out.println(userService.getAllUsers());


        User user = userService.getUserById(1);
        System.out.println(userService.getSafetyUser(user));
    }


}
