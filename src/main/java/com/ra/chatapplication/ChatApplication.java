package com.ra.chatapplication;

import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

public class ChatApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ChatApplication.class, args);

        ConfigurableApplicationContext run = SpringApplication.run(ChatApplication.class, args);
        UserService userService = (UserService) run.getBean("userServiceImpl");
//        ChatService chatService = (ChatService) run.getBean("chatServiceImpl");

        User u1 = new User("John", "Doe", "johndoe@example.com", "mypassword", false);
        User u2 = new User("Jane", "Smith", "janesmith@example.com", "mypassword2", true);
        User u3 = new User("Bob", "Johnson", "bobjohnson@example.com", "mypassword3", false);
        User u4 = new User("Alice", "Williams", "alicewilliams@example.com", "mypassword4", false);
        User u5 = new User("Mark", "Davis", "markdavis@example.com", "mypassword5", true);

        userService.createUser(u1);
        userService.createUser(u2);
        userService.createUser(u3);
        userService.createUser(u4);
        userService.createUser(u5);

        System.out.println(userService.getAllUsers());
    }

}
