package com.pony.services;

import java.util.List;

import com.pony.models.News;


public interface NewsService {
	
	 	List<News> findAll();

	    News findById(Long NewsId);

	    News findBySlug(String slug);
	    
	    News insert(News News);

	    News update(News News);

	    void delete(Long NewsId);
	    
	    News createNews(News news);
	    
	    String formatContent(String content);
}
