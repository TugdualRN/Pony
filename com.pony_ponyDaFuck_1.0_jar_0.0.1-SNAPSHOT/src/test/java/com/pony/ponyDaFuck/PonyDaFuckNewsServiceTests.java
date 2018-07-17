package com.pony.ponyDaFuck;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pony.models.News;
import com.pony.services.NewsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = DataSourceConnectTest.class)
@ActiveProfiles("test")
public class PonyDaFuckNewsServiceTests {

//	@Test
//	public void contextLoads() {
//		News testedNews = new News();
//		assertEquals(testedNews, new News());
//	}
	@Autowired
	private NewsService testedNewsService;
	
	@Test
	public void testFindById(){
			News news = testedNewsService.findById(49L);
			String expected = "toto";
			assertEquals(expected, news.getTitle());
	}
	
	@Test
	public void testFindAll(){
		
		List<News> newsList = testedNewsService.findAll();
		// TODO add test db
		List<News> all = new ArrayList<News>();
			
		assertEquals(all, newsList);
	}
	@Test
	public void testFindBySlug(){
		News news = testedNewsService.findBySlug("slug");
		String expected = "slug";
		assertEquals(expected, news.getSlug());
	}
	@Test
	@Transactional
    @Rollback(true)
	public void testInsert(){
		
		News news = new News();
		
		news.setTitle("the title");
		// TODO news.set...
		
		testedNewsService.insert(news);
		Long newsId = news.getId();
		Long savedNewsId = testedNewsService.findById(newsId).getId();
		assertEquals(newsId, savedNewsId);
	}

//
//	    News update(Long NewsId, News News);
//
//	    void delete(Long NewsId);
//	    
//	    News createNews(News news);
//	    
//	    String formatContent(String content);

}
