package com.travel.proj;

import com.travel.proj.config.sessionConfig.SessionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
public class LoginApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class,args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LoginApplication.class);
    }

    @Bean
    public HttpSessionListener httpSessionListener(){
        return new SessionConfig();
    }

}
