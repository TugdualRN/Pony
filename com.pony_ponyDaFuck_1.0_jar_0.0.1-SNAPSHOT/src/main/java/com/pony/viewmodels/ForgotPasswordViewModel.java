package com.pony.viewmodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ForgotPasswordViewModel {

    @NotNull
    @Size(min = 3, max = 20)
    private String mail;

    public ForgotPasswordViewModel()
    {
        
    }

    public String getMail()
	{
		return this.mail;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
	}
}