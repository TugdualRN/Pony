package com.pony.services;

import java.util.List;

import com.pony.models.News;
import com.pony.models.User;


public interface NewsService {
	
	List<News> findAll();

	News findById(Long NewsId);

	News findBySlug(String slug);
	
	News insert(News News);

	News update(News News);

	void delete(Long NewsId);
	
	String formatContent(String content);

	News createNews(News news, User user);
}
