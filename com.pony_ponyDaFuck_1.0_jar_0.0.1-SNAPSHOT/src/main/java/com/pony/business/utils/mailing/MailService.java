package com.pony.business.utils.mailing;

import java.util.List;

import com.pony.entities.models.User;
import com.pony.entities.models.Token;

public interface MailService {
    
    public boolean sendMail(String to, String subject, String body);

    public boolean sendMail(List<String> to, String subject, String body);

    public boolean SendRegisterMail(User user, Token token);

    public boolean SendResetPassword(User user, Token token);
}