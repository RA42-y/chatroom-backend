package com.ra.chatapplication.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * http://localhost:8080/doc.html#/home
 */
@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j // 开启knife4j
public class SwaggerConfig{

    @Bean
    public Docket docket(Environment environment){
        Contact contact = new Contact("RA42", "https://github.com/RA42-y", "jieni.yu42@gmail.com");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Chatroom API Documentation")
                .description("Chatroom API")
                .version("1.0")
                .contact(contact)
                .build();
//        Profiles profiles = Profiles.of("dev");//设置在那个环境下显示swagger,这里设置环境为dev时显示
//        boolean b = environment.acceptsProfiles(profiles);//判断是不是现在的环境是不是我们想要的环境

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("chatroom-api-v1")
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ra.chatapplication.controller"))
                .build();
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(DEFAULT)
//                .select()
//                //.apis(RequestHandlerSelectors.basePackage("com.example.demo2.controller"))//按照包名扫描
//                //.apis(RequestHandlerSelectors.any())全部扫面
//                //.apis(RequestHandlerSelectors.none())不扫面
//                 .paths(PathSelectors.ant("ChatController"))//过滤指定包下的接口
//                .build();
    }

}

