package com.pony.views.viewmodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pony.views.validation.IPasswordMatch;
import com.pony.views.validation.PasswordsMatch;

import org.hibernate.validator.constraints.NotBlank;

@PasswordsMatch
public class ResetPasswordViewModel implements IPasswordMatch {

    @NotBlank
    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
    private String password;

	@NotBlank
    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
    private String confirmPassword;

	@NotNull
    private long userId;

	@NotBlank
	private String token;
	
	public ResetPasswordViewModel() { }

	public ResetPasswordViewModel(long userId, String token) {
		this.userId = userId;
		this.token = token;
	}

    // <editor-fold defaultstate="collapsed" desc="Getter/Setters">
	public String getPassword() { return this.password; }
	public void setPassword(String password) { this.password = password; }
	public String getConfirmPassword() { return this.confirmPassword; }
	public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
	public long getUserId() { return this.userId; }
	public void setUserId(long userId) { this.userId = userId; }
	public String getToken() { return this.token; }
	public void setToken(String token) { this.token = token; }
	// </editor-fold>
}