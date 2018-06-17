package com.pony.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_Tokens")
public class Token {

    // <editor-fold desc="Fields">
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial", name = "Id")
    private long id;

    @Column(nullable = false)
    private int type;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    private Date creationDate;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getter/Setters">
	public long getId()
	{
		return this.id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public int getType()
	{
		return this.type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getValue()
	{
		return this.value;
	}

	public void setValue(String value)
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
    // </editor-fold>
}
