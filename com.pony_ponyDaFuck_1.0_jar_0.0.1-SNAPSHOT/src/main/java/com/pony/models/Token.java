package com.pony.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pony.enumerations.TokenType;

@Entity
@Table(name = "T_Tokens")
public class Token {

    // <editor-fold desc="Fields">
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial", name = "Id")
	private long id;

    @Column(nullable = false)
    private TokenType type;

    @Column(nullable = false)
    private UUID value;

    @Column(nullable = false)
	private Date creationDate;
	// </editor-fold>
	
	public Token(TokenType type, UUID value, Date creationDate)
	{
		this.type = type;
		this.value = value;
		this.creationDate = creationDate;
	}

    // <editor-fold defaultstate="collapsed" desc="Getter/Setters">
	public long getId()
	{
		return this.id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public TokenType getType()
	{
		return this.type;
	}

	public void setType(TokenType type)
	{
		this.type = type;
	}

	public UUID getValue()
	{
		return this.value;
	}

	public void setValue(UUID value)
	{
		this.value = value;
	}

	public Date getCreationdate()
	{
		return this.creationDate;
	}

	public void setCreationdate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	// public User getUser()
	// {
	// 	return this.user;
	// }

	// public void setUser(User user)
	// {
	// 	this.user = user;
	// }
    // </editor-fold>
}
