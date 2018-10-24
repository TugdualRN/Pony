package com.pony.models;

import java.time.LocalDateTime;

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
import com.pony.enumerations.SocialNetworkType;

@Entity
@Table(name = "T_SocialNetworks")
public class SocialNetwork {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial", name = "Id")
    private long Id;

    @Column(nullable = false)
    public SocialNetworkType socialNetworkType;

    @Column(nullable = false)
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime creationDate;

    private String requestToken;
    private String accessToken;
    private String tokenSecret;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private User user;

    public SocialNetwork() {}

    public SocialNetwork(SocialNetworkType socialNetworkType)
    {
        this.socialNetworkType = socialNetworkType;
        this.creationDate = LocalDateTime.now();
    }

    public SocialNetwork(SocialNetworkType socialNetworkType,
        String accessToken,
        String tokenSecret) {

        this(socialNetworkType);

        this.accessToken = accessToken;
        this.tokenSecret = tokenSecret;
    }

    public SocialNetwork(SocialNetworkType socialNetworkType,
        String requestToken,
        String accessToken,
        String tokenSecret) {
        
        this(socialNetworkType, accessToken, tokenSecret);

        this.requestToken = requestToken;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getter/Setters">
    public long getId() { return this.Id; }
    public void setId(long Id) { this.Id = Id; }
	public SocialNetworkType getSocialNetworkType() { return this.socialNetworkType; }
	public void setSocialNetworkType(SocialNetworkType socialNetworkType) { this.socialNetworkType = socialNetworkType; }
	public LocalDateTime getCreationDate() { return this.creationDate; }
	public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
	public String getRequesttoken() { return this.requestToken; }
	public void setRequesttoken(String requestToken) { this.requestToken = requestToken; }
	public String getAccesstoken() { return this.accessToken; }
	public void setAccesstoken(String accessToken) { this.accessToken = accessToken; }
	public String getTokensecret() { return this.tokenSecret; }
	public void setTokensecret(String tokenSecret) { this.tokenSecret = tokenSecret; }
    public User getUser() { return this.user; }
	public void setUser(User user) { this.user = user; }
    // </editor-fold>
}