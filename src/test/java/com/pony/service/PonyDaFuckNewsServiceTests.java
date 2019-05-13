package com.pony.service;

import com.pony.business.services.NewsService;
import com.pony.business.services.UserService;
import com.pony.entities.models.News;
import com.pony.entities.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PonyDaFuckNewsServiceTests {

	@Autowired
	private NewsService testedNewsService;

	@Autowired
	private UserService testedUserService;

	@Test
	public void testFindById(){
			News news = testedNewsService.findById(1L);
			String expected = "Title";
			assertEquals(expected, news.getTitle());
	}

	@Test
	public void testFindAll(){

		List<News> newsList = testedNewsService.findAll();
		int all = 6;
		assertEquals(all, newsList.size());
	}
	@Test
	public void testFindBySlug(){
		News news = testedNewsService.findBySlug("title");
		String expected = "title";
		assertEquals(expected, news.getSlug());
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testInsert(){

		News news = new News();

		news.setTitle("the title");
		news.setContent("content");
		testedNewsService.insert(news);
		Long newsId = news.getId();
		Long savedNewsId = testedNewsService.findById(newsId).getId();
		assertEquals(newsId, savedNewsId);
	}
	@Test
	@Transactional
    @Rollback(true)
	public void testUpdate(){

		News news = testedNewsService.findById(1L);
		String title = "the title updated";
		news.setTitle(title);
		news = testedNewsService.update(news);

		assertEquals(title, news.getTitle());
	}
	@Test
	@Transactional
    @Rollback(true)
	public void testDelete(){

		testedNewsService.delete(1L);
		News news = testedNewsService.findById(1L);
		assertEquals(null, news);
	}
	@Test
	@Transactional
    @Rollback(true)
	public void testCreateNews(){

		News news = testedNewsService.findById(1L);
		User user = testedUserService.findById(1L);

		news = testedNewsService.createNews(news, user);
		assertEquals(user, news.getAuthor());
	}

//
//	    String formatContent(String content);

}
