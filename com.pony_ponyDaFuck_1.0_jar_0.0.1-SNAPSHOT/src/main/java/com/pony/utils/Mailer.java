package com.pony.utils;

import java.util.ArrayList;
import java.util.List;

import com.pony.models.User;
import com.pony.models.Token;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class Mailer {

    private MailSender _mailSender;

    private String _sender;

    public Mailer(MailSender mailSender, String sender)
    {
        _mailSender = mailSender;
        _sender = sender;
    }

    public void sendMail(String to, String subject, String body) {
        List<String> lst = new ArrayList<String>();
        lst.add(to);

        sendMail(lst, subject, body);
    }

    public void sendMail(List<String> to, String subject, String body) {
        
        SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(_sender);
		message.setTo(to.toArray(new String[to.size()]));
		message.setSubject(subject);
        message.setText(body);
        
		_mailSender.send(message);
    }

    public void SendRegisterMail(User user, Token token) {
        // load templating instead

        String url = "localhost:8000/confirm-mail?userId=" + user.getId() + "&tokenValue=" + token.getValue();

        String html = 
            "<p>Welcom blablabla, please clic <a href='" + url + "'>here</a> to confirm your account</p>";

        sendMail(user.getMail(), "Welcome", html);
    }

    public void SendResetPassword(User user, Token token) {
        // load templating instead
        String html = 
            "<p>To change your password, please follow the link: localhost:8000/confirm-mail?userId=" 
            + user.getId() 
            + "&tokenValue=" + token.getValue();

        sendMail(user.getMail(), "Welcome", html);
    }
}