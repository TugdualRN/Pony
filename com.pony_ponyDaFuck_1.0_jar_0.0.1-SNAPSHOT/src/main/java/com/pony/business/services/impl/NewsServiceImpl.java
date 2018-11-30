package com.pony.business.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.slugify.Slugify;
import com.pony.entities.models.News;
import com.pony.entities.models.User;
import com.pony.data.repositories.NewsRepository;
import com.pony.business.services.NewsService;

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
		Optional<News> news = _newsRepository.findById(newsId);
        if (news.isPresent()) {
            return news.get();
        } else {
            return null;
        }
    }

    @Override
    public News insert(News news){

        return _newsRepository.save(news);
    }

    @Override
    public News update(News news) {
        return _newsRepository.save(news);
    }

    @Override
    @Transactional
    public void delete(Long newsId) {
    	_newsRepository.deleteById(newsId);
    }

	@Override
	public News findBySlug(String slug) {

		return _newsRepository.findBySlug(slug);
	}

	@Override
	public News createNews(News news, User user) {

		news.setCreationDate(LocalDateTime.now());
		news.setSlug(this.Slugify(news.getTitle()));
		news.setAuthor(user);

		News savedNews = _newsRepository.save(news);

		if (savedNews != null) {
			// log
			return savedNews;
		}

		return null;
	}

	@Override
	public String formatContent(String content) {
		// TO DO Auto-generated method stub

		// content = content.substring(1, content.length() - 1).replace("\\", "");

		// if (content.indexOf("<img src=\"") > -1) {

		// }
		/*
		 * try { byte[] bytes = file.getBytes();
		 * 
		 * // Creating the directory to store file String rootPath =
		 * System.getProperty("catalina.home"); File dir = new File(rootPath +
		 * File.separator + "tmpFiles"); if (!dir.exists()) dir.mkdirs();
		 * 
		 * // Create the file on server File serverFile = new File(dir.getAbsolutePath()
		 * + File.separator + name); BufferedOutputStream stream = new
		 * BufferedOutputStream( new FileOutputStream(serverFile)); stream.write(bytes);
		 * stream.close();
		 * 
		 * logger.info("Server File Location=" + serverFile.getAbsolutePath());
		 * 
		 * return "You successfully uploaded file=" + name; } catch (Exception e) {
		 * return "You failed to upload " + name + " => " + e.getMessage(); }
		 */

		return content;
	}

	@Override
	public List<News> findByLangOrderByIdDesc(String lang) {

		return _newsRepository.findByLangOrderByIdDesc(lang);
	}

	private String Slugify(String title) {

		Slugify slg = new Slugify();
		String slugedTitle = slg.slugify(title);

		int count = _newsRepository.findBySlugLike(slugedTitle);

		if (count != 0) {
			slugedTitle += "_" + count;
		}

		return slugedTitle;
	}
}