package com.pony.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "T_Users")
public class User {

    // <editor-fold desc="Fields">
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial", name = "Id")
    private long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String userName;

    /**
     *      Uppercase field used for comparaison
     *      Prevent potential userName duplicata (like toto/Toto/tOtO/TOTO)
     */
    @Column(unique = true, nullable = false)
    private String normalizedUserName;


    @Column(nullable = false)
    private String passwordHash;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String mail;

    /**
     *      Uppercase field used for comparaison
     *      Same as normalizedUserName
     */
    @Column(unique = true, nullable = false)
    private String normalizedMail;

    private String firstName;

    private String lastName;

    private String phone;

    @ManyToMany(fetch = FetchType.EAGER,
    cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable( 
        name = "T_user_roles", 
        joinColumns =           { @JoinColumn(name = "user_id")}, 
        inverseJoinColumns =    { @JoinColumn(name = "role_id")}
    )
    private List<Role> roles = new ArrayList<Role>();

    @OneToMany(
        cascade = CascadeType.ALL, 
        orphanRemoval = true
    )
    //@JoinColumn(name = "user_id")
    private List<Token> tokens = new ArrayList<Token>();

    @Column(nullable = false)
    private boolean isActive = false;

    @Column(nullable = false)
    private boolean isBanned = false;

    @Column(nullable = false)
    private boolean isSuspended = false;
    // </editor-fold>

    // <editor-fold desc="Constructors">
    public User() {
    }

    public User(String userName, String mail) {
        this.userName = userName;
        this.mail = mail;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getter/Setters">
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Token> getTokens() {
        return this.tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public String getNormalizedUserName()
	{
		return this.normalizedUserName;
	}

	public void setNormalizedUserName(String normalizedMail)
	{
		this.normalizedUserName = normalizedMail;
    }
    
    public String getNormalizedMail()
	{
		return this.normalizedMail;
	}

	public void setNormalizedMail(String normalizedMail)
	{
		this.normalizedMail = normalizedMail;
    }
    
    public boolean getIsActive()
	{
		return this.isActive;
	}

	public void setIsActive(boolean isActive)
	{
		this.isActive = isActive;
    }
    
    public boolean getIsBanned()
	{
		return this.isBanned;
	}

	public void setIsBanned(boolean isBanned)
	{
		this.isBanned = isBanned;
    }
    
	public boolean getIsSuspended()
	{
		return this.isSuspended;
	}

	public void setIsSuspended(boolean isSuspended)
	{
		this.isSuspended = isSuspended;
	}
    // </editor-fold>

    @Override
    public String toString() {
        return "Users" + 
            "{" + "\n"        +
            "\tid = "         + id            + "\n" + 
            "\tlogin = "      + userName      + "\n" + 
            "\tpassword = "   + passwordHash  + "\n" + 
            "\tfirstName ="   + firstName     + "\n" + 
            "\tlastName = "   + lastName      + "\n" + 
            "\tphone ="       + phone         + "\n" + 
            "\tmail ="        + mail          + "\n" + 
            "\troles = "      + roles         + "\n" + 
            "}";
    }
}