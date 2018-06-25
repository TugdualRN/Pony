package com.pony.viewmodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

//@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class RegisterViewModel {

    @NotNull
    @Size(min = 3, max = 15)
    private String userName;

    @NotNull
    @Email
    private String mail;

    @NotNull
    @Size(min = 8, max = 15)
    private String password;

    @NotNull
    @Size(min = 8, max = 15)
    private String confirmPassword;

    public RegisterViewModel()
    {

    }

    // <editor-fold defaultstate="collapsed" desc="Getter/Setters">
	public String getUserName()
	{
		return this.userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
    }
    
    public String getMail()
	{
		return this.mail;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
	}
    
    public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String password)
	{
		this.password = password;
    }
    
	public String getConfirmPassword()
	{
		return this.confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
    }
    // </editor-fold>
}