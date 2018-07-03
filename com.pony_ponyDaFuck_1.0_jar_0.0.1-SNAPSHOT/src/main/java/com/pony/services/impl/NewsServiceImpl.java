package com.pony.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pony.models.News;
import com.pony.repositories.NewsRepository;
import com.pony.services.NewsService;

@Service
public class NewsServiceImpl implements NewsService {

	 private final NewsRepository _newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        this._newsRepository = newsRepository;
    }
	
    @Override
    @Transactional(readOnly = true)
    public List<News> findAll() {
        return _newsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public News findById(Long newsId){

        News news = _newsRepository.findOne(newsId);
        
        return news;
    }

    @Override
    public News insert(News news){

        return _newsRepository.save(news);
    }

    @Override
    public News update(Long newsId, News news) {

        News newsById = _newsRepository.findOne(newsId);

        if (newsById != null) {
        	newsById.setTitle(news.getTitle());
        	newsById.setContent(news.getContent());
        	newsById.setSlug(news.getSlug());
//        	newsById.setAuthor(news.getAuthor());
        }

        

        return _newsRepository.save(newsById);
    }

    @Override
    @Transactional
    public void delete(Long newsId) {
    	_newsRepository.delete(newsId);
    }

	@Override
	public News findBySlug(String slug) {
	
		return _newsRepository.findBySlug(slug);
	}
}