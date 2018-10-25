package com.pony.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ComponentScan("com.pony")
public class UtilityClassesConfig {


    @Value("${mailing.host}")
    private String _host;

    @Value("${mailing.port}")
    private int _port;

    @Value("${mailing.sender}")
    private String _sender;

    @Value("${mailing.mail}")
    private String _mail;

    @Value("${mailing.password}")
    private String _password;

    @Bean
    public MailSender mailSender() {
        
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(_host);
        javaMailSender.setPort(_port);
        javaMailSender.setUsername(_mail);
        javaMailSender.setPassword(_password);

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return javaMailSender;
    }
}