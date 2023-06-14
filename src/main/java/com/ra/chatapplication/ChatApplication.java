package com.ra.chatapplication;

import com.ra.chatapplication.model.entity.Chat;
import com.ra.chatapplication.model.entity.User;
import com.ra.chatapplication.service.ChatService;
import com.ra.chatapplication.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableJpaAuditing
public class ChatApplication {

    public static void main(String[] args) throws ParseException {
//        SpringApplication.run(ChatApplication.class, args);

        ConfigurableApplicationContext run = SpringApplication.run(ChatApplication.class, args);
        UserService userService = (UserService) run.getBean("userServiceImpl");
        ChatService chatService = (ChatService) run.getBean("chatServiceImpl");

        User u1 = userService.createUser("John", "Doe", "johndoe@example.com", "mypassword", false);
        User u2 = userService.createUser("Jane", "Smith", "janesmith@example.com", "mypassword2", true);
        User u3 = userService.createUser("Bob", "Johnson", "bobjohnson@example.com", "mypassword3", false);
        User u4 = userService.createUser("Alice", "Williams", "alicewilliams@example.com", "mypassword4", false);
        User u5 = userService.createUser("Mark", "Davis", "markdavis@example.com", "mypassword5", true);
        User u6 = userService.createUser("Sarah", "Lee", "sarahlee@example.com", "mypassword6", false);
        User u7 = userService.createUser("David", "Nguyen", "davidnguyen@example.com", "mypassword7", true);
        User u8 = userService.createUser("Emily", "Garcia", "emilygarcia@example.com", "mypassword8", false);
        User u9 = userService.createUser("Michael", "Taylor", "michaeltaylor@example.com", "mypassword9", false);
        User u10 = userService.createUser("Samantha", "Brown", "samanthabrown@example.com", "mypassword10", true);
        User u11 = userService.createUser("William", "Jones", "williamjones@example.com", "mypassword11", false);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date expirationDate = dateFormat.parse("01/01/2024 00:00:00");

        Chat c1 = chatService.createChat("Chat 1", "Chat created by " + u1.getFullName(), expirationDate, u1);
        Chat c2 = chatService.createChat("Chat 2", "Chat created by " + u2.getFullName(), expirationDate, u2);
        Chat c3 = chatService.createChat("Chat 3", "Chat created by " + u3.getFullName(), expirationDate, u3);
        Chat c4 = chatService.createChat("Chat 4", "Chat created by " + u1.getFullName(), expirationDate, u1);
        Chat c5 = chatService.createChat("Chat 5", "Chat created by " + u4.getFullName(), expirationDate, u4);
        Chat c6 = chatService.createChat("Chat 6", "Chat created by " + u5.getFullName(), expirationDate, u5);
        Chat c7 = chatService.createChat("Chat 7", "Chat created by " + u3.getFullName(), expirationDate, u3);
        Chat c8 = chatService.createChat("Chat 8", "Chat created by " + u1.getFullName(), expirationDate, u1);


        chatService.addMemberToChat(c1, u2);
        chatService.addMemberToChat(c1, u3);
        chatService.addMemberToChat(c1, u4);

        chatService.saveChat(c1);

        chatService.addMemberToChat(c2, u3);
        chatService.addMemberToChat(c2, u4);
        chatService.addMemberToChat(c2, u5);

        chatService.saveChat(c2);

        chatService.addMemberToChat(c3, u1);
        chatService.addMemberToChat(c3, u4);
        chatService.addMemberToChat(c3, u5);
        chatService.addMemberToChat(c3, u6);

        chatService.saveChat(c3);

        chatService.addMemberToChat(c4, u2);
        chatService.addMemberToChat(c4, u5);
        chatService.addMemberToChat(c4, u6);
        chatService.addMemberToChat(c4, u7);

        chatService.saveChat(c4);

        chatService.addMemberToChat(c5, u1);
        chatService.addMemberToChat(c5, u3);
        chatService.addMemberToChat(c5, u6);
        chatService.addMemberToChat(c5, u7);
        chatService.addMemberToChat(c5, u8);

        chatService.saveChat(c5);

        chatService.addMemberToChat(c6, u1);
        chatService.addMemberToChat(c6, u2);
        chatService.addMemberToChat(c6, u4);
        chatService.addMemberToChat(c6, u6);

        chatService.saveChat(c6);

        chatService.addMemberToChat(c7, u1);
        chatService.addMemberToChat(c7, u4);
        chatService.addMemberToChat(c7, u6);

        chatService.saveChat(c7);

        chatService.addMemberToChat(c8, u1);
        chatService.addMemberToChat(c8, u4);
        chatService.addMemberToChat(c8, u6);

        chatService.saveChat(c8);

        System.out.println(userService.getAllUsers());
    }
}
