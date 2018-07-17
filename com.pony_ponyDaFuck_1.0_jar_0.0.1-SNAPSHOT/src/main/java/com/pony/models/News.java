package com.pony.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_News")
public class News {

	@Id
	@GeneratedValue
	@Column(name = "Id")
	private long id;

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;
	
	@Column(nullable = false)
	private String slug;

	@JoinColumn(name="user_id")
	@ManyToOne(fetch=FetchType.LAZY)
	private User author;
	
	@Column(nullable = false)
	private LocalDateTime creationDate;

	private LocalDateTime modificationDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(LocalDateTime modificationDate) {
		this.modificationDate = modificationDate;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", content=" + content + ", slug=" + slug + ", author=" + author
				+ ", creationDate=" + creationDate + ", modificationDate=" + modificationDate + "]";
	}
}