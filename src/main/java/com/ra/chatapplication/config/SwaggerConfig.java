package com.ra.chatapplication.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


/**
 * SwaggerConfig is a configuration class for Swagger and Knife4j API documentation.
 * The generated documentation can be accessed at http://localhost:8080/doc.html#/home.
 */
@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
public class SwaggerConfig {

    /**
     * Creates a Docket bean for API documentation generation.
     *
     * @param environment The Spring environment
     * @return Docket bean for API documentation
     */
    @Bean
    public Docket docket(Environment environment) {
        Contact contact = new Contact("RA42", "https://github.com/RA42-y", "jieni.yu42@gmail.com");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Chatroom API Documentation")
                .description("Chatroom API")
                .version("1.0")
                .contact(contact)
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("chatroom-api-v1")
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ra.chatapplication.controller"))
                .build();
    }

}

