package com.travel.proj.config.emailConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

   // @Value("${email.id}")
    private static String username = "dininglab.korea@gmail.com";

  //  @Value("${email.pwd}")
    private static String password ="dbguswns123";

    @Bean
    public static JavaMailSender mailSender() {
        JavaMailSenderImpl jms = new JavaMailSenderImpl();
        jms.setHost("smtp.gmail.com");
        jms.setPort(587);
        jms.setUsername(username);
        jms.setPassword(password);

        Properties prop = new Properties();
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.starttls.enable", "true");

        prop.setProperty("mail.debug", "true");
        jms.setJavaMailProperties(prop);

        return jms;
    }
}
