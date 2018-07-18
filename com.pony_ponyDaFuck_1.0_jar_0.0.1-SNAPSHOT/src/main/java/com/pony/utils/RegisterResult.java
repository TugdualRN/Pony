package com.pony.utils;

import com.pony.models.User;

public class RegisterResult {
    
    private boolean isUserNameAlreadyTaken = false;

    private boolean isMailAlreadyTaken = false;

    private User user;

	public boolean getIsUserNameAlreadyTaken()
	{
		return this.isUserNameAlreadyTaken;
	}

	public void setIsUserNameAlreadyTaken(boolean isUserNameAlreadyTaken)
	{
		this.isUserNameAlreadyTaken = isUserNameAlreadyTaken;
	}

	public boolean getIsMailAlreadyTaken()
	{
		return this.isMailAlreadyTaken;
	}

	public void setIsMailAlreadyTaken(boolean isMailAlreadyTaken)
	{
		this.isMailAlreadyTaken = isMailAlreadyTaken;
    }
    
    public User getUser()
	{
		return this.user;
	}

	public void setUser(User user)
	{
		this.user = user;
    }
    
    public boolean isValid() {
        return this.user != null && !isUserNameAlreadyTaken && !isMailAlreadyTaken;
    }
}