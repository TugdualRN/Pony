package com.pony.config;

import com.pony.utils.Mailer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

@Configuration
@ComponentScan("com.pony")
public class UtilityClassesConfig {

    private String _sender = "unicorn.factory.sender@gmail.com";

    @Bean
    public Mailer mailer(MailSender mailSender) {
        return new Mailer(mailSender, _sender);
    }
}