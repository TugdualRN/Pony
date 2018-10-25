package com.pony.business.utils;

import com.pony.models.User;

public class RegisterResult {
    
    private boolean isUserNameAlreadyTaken = false;

	private boolean isMailAlreadyTaken = false;
	
	private boolean passwordMatches = true;

	private User user;

	public RegisterResult() { }
	
	public RegisterResult(boolean passwordMatches) {
		this.passwordMatches = passwordMatches;
	}

	public boolean getIsUserNameAlreadyTaken() {
		return this.isUserNameAlreadyTaken;
	}

	public void setIsUserNameAlreadyTaken(boolean isUserNameAlreadyTaken) {
		this.isUserNameAlreadyTaken = isUserNameAlreadyTaken;
	}

	public boolean getIsMailAlreadyTaken() {
		return this.isMailAlreadyTaken;
	}

	public void setIsMailAlreadyTaken(boolean isMailAlreadyTaken) {
		this.isMailAlreadyTaken = isMailAlreadyTaken;
    }
    
    public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean getPasswordMatches() {
		return this.passwordMatches;
	}

	public void setPasswordMatches(boolean passwordMatch) {
		this.passwordMatches = passwordMatch;
	}
    
    public boolean isValid() {
        return this.user != null && !isUserNameAlreadyTaken && !isMailAlreadyTaken;
    }
}