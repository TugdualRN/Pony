package com.pony.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.slugify.Slugify;
import com.pony.models.News;
import com.pony.models.User;
import com.pony.repositories.NewsRepository;
import com.pony.repositories.UserRepository;
import com.pony.security.ConnectedUserDetails;
import com.pony.services.NewsService;

@Service
public class NewsServiceImpl implements NewsService {

	 private final NewsRepository _newsRepository;
	 private final UserRepository _userRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, UserRepository userRepository) {
        this._newsRepository = newsRepository;
        this._userRepository = userRepository;
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
        	newsById.setAuthor(news.getAuthor());
        	newsById.setModificationDate(LocalDateTime.now());
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

	@Override
	public News createNews(News news) {
		
		news.setCreationDate(LocalDateTime.now());
		
		// get user in session
		Object userConnected = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// test if user
		User user = _userRepository.findByMail(((ConnectedUserDetails) userConnected).getUsername());
		
		if((User)user instanceof User) {
			news.setAuthor((User)user);
		}
		
		Slugify slg = new Slugify();
		String slugifyTitle = slg.slugify(news.getTitle());
		
		News oldNews = _newsRepository.findBySlug(slugifyTitle);
		
		int index = 0;
		String slug = slugifyTitle;
		while (oldNews != null) {
			slug = slugifyTitle + "_" + index;
			index++;
			oldNews = _newsRepository.findBySlug(slug);
		}
		news.setSlug(slug);
		try {			
			_newsRepository.save(news);
		} catch (Exception e) {
			System.out.println("\n FOUND AN APPLICATION EXCEPTION \n");
			System.out.println(e);
			return null;
		}
	
		return news;
	}
}