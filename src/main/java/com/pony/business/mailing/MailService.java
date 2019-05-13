package com.pony.business.mailing;

import com.pony.entities.models.Token;
import com.pony.entities.models.User;

import java.util.List;

public interface MailService {
    
    public boolean sendMail(String to, String subject, String body);

    public boolean sendMail(List<String> to, String subject, String body);

    public boolean SendRegisterMail(User user, Token token);

    public boolean SendResetPassword(User user, Token token);
}