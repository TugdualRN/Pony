package com.pony.business.mailing;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.pony.entities.models.User;
import com.pony.entities.models.Token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender _mailSender;

    @Value("${mailing.sender}")
    private String _sender;

    public MailServiceImpl(JavaMailSender mailSender) {
        _mailSender = mailSender;
    }

    /**
     * Send Mail to one person
     */
    public boolean sendMail(String to, String subject, String body) {
        List<String> lst = new ArrayList<String>();
        lst.add(to);

        return sendMail(lst, subject, body);
    }

    /**
     * Send Mail to multiple people
     */
    public boolean sendMail(List<String> to, String subject, String body) {
        return sendMailImpl(to, subject, body);
    }

    /**
     * Send a Register Mail
     */
    public boolean SendRegisterMail(User user, Token token) {
        // load templating instead
        String url = String.format(
            "localhost:8000/confirm-mail?userId=%d&tokenValue=%s", user.getId(), token.getValue());
        String html = String.format(
            "<p>To confirm your account, click <a href='%s'>here</a></p>", url);

        return sendMail(user.getMail(), "Welcome", html);
    }

    /**
     * Send a Reset Password Mail
     */
    public boolean SendResetPassword(User user, Token token) {
        // load templating instead
        String url = String.format(
        "localhost:8000/change-password?userId=%d&tokenValue=%s", user.getId(), token.getValue());
        String html = String.format(
        "<p>To reset your password, click <a href='%s'>here</a></p>", url);

        return sendMail(user.getMail(), "Password Reset", html);
    }

    /**
     * Mailing Implementation
     * 
     * @param to            Receivers
     * @param subject       Mail Subject
     * @param body          Mail Content
     * @return              True on success, false otherwise
     */
    private boolean sendMailImpl(List<String> to, String subject, String body) {
        try {
            MimeMessage mimeMessage = _mailSender.createMimeMessage();
            mimeMessage.setContent(body, "text/html");

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            helper.setFrom(_sender);
            helper.setTo(to.toArray(new String[to.size()]));
            helper.setSubject(subject);
            
            _mailSender.send(mimeMessage);

            return true;
        }
        catch (MessagingException e) {
            return false;
        }
    }
}