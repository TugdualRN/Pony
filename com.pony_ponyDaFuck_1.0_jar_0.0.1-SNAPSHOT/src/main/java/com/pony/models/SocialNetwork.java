package com.pony.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.pony.converters.LocalDateTimeConverter;

public class SocialNetwork {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial", name = "Id")
    private long Id;

    @Column(nullable = false)
    public SocialNetwork socialNetwork;

    @Column(nullable = false)
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime creationDate;

    @Column(nullable = false)
	@JoinColumn(name="user_id", nullable = true)
	private User user;

    public SocialNetwork() {}

    public SocialNetwork(SocialNetwork socialNetwork)
    {
        this.socialNetwork = socialNetwork;
        this.user = user;
        this.creationDate = new LocalDateTime();
    }

    // <editor-fold defaultstate="collapsed" desc="Getter/Setters">
    public long getId() { return this.Id; }
    public void setId(long Id) { this.Id = Id; }
	public SocialNetwork getSocialNetwork() { return this.socialNetwork; }
	public void setSocialNetwork(SocialNetwork socialNetwork) { this.socialNetwork = socialNetwork; }
	public LocalDateTime getCreationDate() { return this.creationDate; }
	public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
	public User getUser() { return this.user; }
	public void setUser(User user) { this.user = user; }
    // </editor-fold>
}