package com.pony.business.utils.mailing;

import java.util.ArrayList;
import java.util.List;

import com.pony.entities.models.User;
import com.pony.entities.models.Token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private MailSender _mailSender;

    @Value("${mailing.sender}")
    private String _sender;

    public MailServiceImpl(MailSender mailSender) {
        _mailSender = mailSender;
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
        String url = String.format(
            "localhost:8000/confirm-mail?userId=%d&tokenValue=%s", user.getId(), token.getValue());
        String html = String.format(
            "<p>To confirm your account, click <a href='%s'>here</a> to confirm your account</p>", url);

        sendMail(user.getMail(), "Welcome", html);
    }

    public void SendResetPassword(User user, Token token) {
        // load templating instead
        String url = String.format(
        "localhost:8000/confirm-mail?userId=%d&tokenValue=%s", user.getId(), token.getValue());
        String html = String.format(
        "<p>To reset your password, click <a href='%s'>here</a> to confirm your account</p>", url);

        sendMail(user.getMail(), "Password Reset", html);
    }
}