package com.pony.business.services;

import java.util.List;
import java.util.Optional;

import com.pony.entities.models.News;
import com.pony.entities.models.User;


public interface NewsService {
	
	List<News> findAll();

	Optional<News> findById(Long NewsId);

	News findBySlug(String slug);
	
	News insert(News News);

	News update(News News);

	void delete(Long NewsId);
	
	String formatContent(String content);

	News createNews(News news, User user);
		
	List<News> findByLangOrderByIdDesc(String lang);
}
