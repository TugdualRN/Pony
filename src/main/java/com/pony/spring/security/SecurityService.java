package com.pony.spring.security;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String normalizedMail, String password);
}