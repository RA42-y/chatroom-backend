package com.ra.chatapplication;

import com.ra.chatapplication.utils.EmailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;

@SpringBootTest
public class MailTest {

    @Autowired
    private EmailUtils emailUtils;

    @Resource
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail(){
        emailUtils.sendMail("xxxxxxx@gmail.com", "text mail", "hi！");
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "test user");

        String content = templateEngine.process("email/default-password-email-test", context);
        System.out.println(content);

        emailUtils.sendMail("xxxxxxx@gmail.com", "html mail", content);

    }

}
