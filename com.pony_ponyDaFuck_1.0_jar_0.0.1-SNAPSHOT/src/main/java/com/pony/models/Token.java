package com.pony.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.pony.data.converters.LocalDateTimeConverter;

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
	@Enumerated(EnumType.STRING)
    private TokenType type;

    @Column(nullable = false)
    private UUID value;

	@Column(nullable = false)
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime creationDate;

	@Column(nullable = false)
	private boolean consumed;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime consumationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	// </editor-fold>
	
	public Token() {}

	public Token(TokenType type) {
		super();
		this.type = type;
		this.value = UUID.randomUUID();
		this.creationDate = LocalDateTime.now();
	}

    // <editor-fold defaultstate="collapsed" desc="Getter/Setters">
	public long getId() { return this.id; }
	public void setId(long id) { this.id = id; }

	public TokenType getType() { return this.type; }
	public void setType(TokenType type) { this.type = type; }

	public UUID getValue() { return this.value; }
	public void setValue(UUID value) { this.value = value; }

	public LocalDateTime getCreationdate() { return this.creationDate; }
	public void setCreationdate(LocalDateTime creationDate) { this.creationDate = creationDate; }

	public boolean getConsumed() { return this.consumed; }
	public void setConsumed(boolean consumed) { this.consumed = consumed; }

	public LocalDateTime getConsumationdate() { return this.consumationDate; }
	public void setConsumationdate(LocalDateTime consumationDate) { this.consumationDate = consumationDate;}
	
	public User getUser() { return this.user; }
	public void setUser(User user) { this.user = user; }
	// </editor-fold>
}