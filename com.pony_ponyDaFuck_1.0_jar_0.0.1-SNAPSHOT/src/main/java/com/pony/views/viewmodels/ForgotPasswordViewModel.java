package com.pony.views.viewmodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ForgotPasswordViewModel {

    @NotNull
    @Size(min = 3, max = 20)
    private String mail;

    public ForgotPasswordViewModel() {}

    // <editor-fold defaultstate="collapsed" desc="Getter/Setters">
    public String getMail() { return this.mail; }
	public void setMail(String mail) { this.mail = mail; }
    // </editor-fold>
}