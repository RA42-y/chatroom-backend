package com.ra.chatapplication;

import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.service.UserService;
import com.ra.chatapplication.service.ChatService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class ChatApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ChatApplication.class, args);

        ConfigurableApplicationContext run = SpringApplication.run(ChatApplication.class, args);
        UserService userService = (UserService) run.getBean("userServiceImpl");
        ChatService chatService = (ChatService) run.getBean("chatServiceImpl");

        userService.createUser("John", "Doe", "johndoe@example.com", "mypassword", false);
        userService.createUser("Jane", "Smith", "janesmith@example.com", "mypassword2", true);
        userService.createUser("Bob", "Johnson", "bobjohnson@example.com", "mypassword3", false);
        userService.createUser("Alice", "Williams", "alicewilliams@example.com", "mypassword4", false);
        userService.createUser("Mark", "Davis", "markdavis@example.com", "mypassword5", true);
        userService.createUser("Sarah", "Lee", "sarahlee@example.com", "mypassword6", false);
        userService.createUser("David", "Nguyen", "davidnguyen@example.com", "mypassword7", true);
        userService.createUser("Emily", "Garcia", "emilygarcia@example.com", "mypassword8", false);
        userService.createUser("Michael", "Taylor", "michaeltaylor@example.com", "mypassword9", false);
        userService.createUser("Samantha", "Brown", "samanthabrown@example.com", "mypassword10", true);
        userService.createUser("William", "Jones", "williamjones@example.com", "mypassword11", false);


        User user1 = userService.getUserById(1);
        User user2 = userService.getUserById(2);
        User user3 = userService.getUserById(3);

        chatService.createChat("Chat 1", "Chat created by user 1", user1);
        chatService.createChat("Chat 2", "Chat created by user 2", user2);
        chatService.createChat("Chat 3", "Chat created by user 3", user3);

//        chatService.addMemberToChat(c1, u4);
//        chatService.addMemberToChat(c1, u5);
//        chatService.addMemberToChat(c1, u6);
//        chatService.addMemberToChat(c1, u3);
//
//        chatService.addMemberToChat(c2, u4);
//        chatService.addMemberToChat(c2, u6);

        System.out.println(userService.getAllUsers());
    }

}
