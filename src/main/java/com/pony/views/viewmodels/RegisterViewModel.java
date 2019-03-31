package com.pony.views.viewmodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pony.views.validation.IPasswordMatch;
import com.pony.views.validation.PasswordsMatch;
import com.pony.views.validation.ValidMail;

@PasswordsMatch
public class RegisterViewModel implements IPasswordMatch {

	@NotNull(message = "Username cant be blank")
    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
    private String userName;

	@NotNull(message = "Email cant be blank")
	@ValidMail(message = "Email is not a valid Email")
    private String mail;

	@NotNull(message = "Password cant be empty")
    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
    private String password;

    private String confirmPassword;

    public RegisterViewModel() {
	}
	
	public RegisterViewModel(String userName, String mail) {
		this.userName = userName;
		this.mail = mail;
	}

    // <editor-fold defaultstate="collapsed" desc="Getter/Setters">
	public String getUserName() { return this.userName; }
	public void setUserName(String userName) { this.userName = userName; }
    public String getMail() { return this.mail; }
	public void setMail(String mail) { this.mail = mail; }
    public String getPassword() { return this.password; }
	public void setPassword(String password) { this.password = password; }
	public String getConfirmPassword() { return this.confirmPassword; }
	public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    // </editor-fold>
}