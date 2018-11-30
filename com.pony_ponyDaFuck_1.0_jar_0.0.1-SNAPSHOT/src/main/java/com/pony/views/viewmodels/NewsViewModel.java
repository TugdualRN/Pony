package com.pony.views.viewmodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewsViewModel {

	@NotNull
	@Size(min = 3, max = 100)
	private String title;

	@NotNull
	// @Size(min = 10)
	private String content;

	@NotNull
	@Size(min = 3, max = 250)
	private String description;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}