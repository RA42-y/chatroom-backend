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
        User u6 = new User("Sarah", "Lee", "sarahlee@example.com", "mypassword6", false);
        User u7 = new User("David", "Nguyen", "davidnguyen@example.com", "mypassword7", true);
        User u8 = new User("Emily", "Garcia", "emilygarcia@example.com", "mypassword8", false);
        User u9 = new User("Michael", "Taylor", "michaeltaylor@example.com", "mypassword9", false);
        User u10 = new User("Samantha", "Brown", "samanthabrown@example.com", "mypassword10", true);
        User u11 = new User("William", "Jones", "williamjones@example.com", "mypassword11", false);
        User u12 = new User("Megan", "Miller", "meganmiller@example.com", "mypassword12", true);
        User u13 = new User("Kevin", "Moore", "kevinmoore@example.com", "mypassword13", false);
        User u14 = new User("Rachel", "Clark", "rachelclark@example.com", "mypassword14", false);
        User u15 = new User("Jason", "Thomas", "jasonthomas@example.com", "mypassword15", true);

        User u16 = new User("Olivia", "White", "oliviawhite@example.com", "mypassword16", false);
        User u17 = new User("Brandon", "Lee", "brandonlee@example.com", "mypassword17", true);
        User u18 = new User("Ava", "Wilson", "avawilson@example.com", "mypassword18", false);
        User u19 = new User("Ethan", "Jackson", "ethanjackson@example.com", "mypassword19", false);
        User u20 = new User("Natalie", "Anderson", "natalieanderson@example.com", "mypassword20", true);

        User u21 = new User("Jacob", "Harris", "jacobharris@example.com", "mypassword21", false);
        User u22 = new User("Madison", "Martin", "madisonmartin@example.com", "mypassword22", true);
        User u23 = new User("Noah", "Garcia", "noahgarcia@example.com", "mypassword23", false);
        User u24 = new User("Isabella", "Rodriguez", "isabellarodriguez@example.com", "mypassword24", false);
        User u25 = new User("Elijah", "Davis", "elijahdavis@example.com", "mypassword25", true);

        userService.createUser(u1);
        userService.createUser(u2);
        userService.createUser(u3);
        userService.createUser(u4);
        userService.createUser(u5);
        userService.createUser(u6);
        userService.createUser(u7);
        userService.createUser(u8);
        userService.createUser(u9);
        userService.createUser(u10);
        userService.createUser(u11);
        userService.createUser(u12);
        userService.createUser(u13);
        userService.createUser(u14);
        userService.createUser(u15);
        userService.createUser(u16);
        userService.createUser(u17);
        userService.createUser(u18);
        userService.createUser(u19);
        userService.createUser(u20);
        userService.createUser(u21);
        userService.createUser(u22);
        userService.createUser(u23);
        userService.createUser(u24);
        userService.createUser(u25);


        System.out.println(userService.getAllUsers());
    }

}
