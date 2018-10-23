package com.pony.viewmodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class LoginViewModel {

    @NotBlank
    @Size(min = 3, max = 20)
    private String mail;

    @NotBlank
    @Size(min = 8, max = 15)
    private String password;

    @NotNull
    private boolean rememberMe;

    // <editor-fold defaultstate="collapsed" desc="Getter/Setters">
    public String getMail() { return this.mail; }
	public void setMail(String mail) { this.mail = mail; }
	public String getPassword() { return this.password; }
	public void setPassword(String password) { this.password = password; }
	public boolean getRememberMe() { return this.rememberMe; }
	public void setRememberMe(boolean rememberMe) { this.rememberMe = rememberMe; }
	// </editor-fold>
}