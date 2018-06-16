package com.pony.utils;

import java.util.Properties;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Mailer {

    private String _host = "smtp.gmail.com";
    private String _sender = "unicorn.factory.sender@gmail.com";
    private String _pass = "azerty1234!";

    public Mailer()
    {

    }

    public Mailer(String host, String sender, String pass)
    {
        _host = host;
        _sender = sender;
        _pass = pass;
    }

    private void sendFromGMail(String pass, String[] to, String subject, String body) {
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", _host);
        properties.put("mail.smtp.user", _sender);
        properties.put("mail.smtp.password", _pass);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        //MimeMessage mime = new MimeMessage();

    }
}