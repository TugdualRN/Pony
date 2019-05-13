package com.pony.repository;

import com.pony.data.repositories.NewsRepository;
import com.pony.entities.models.News;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class NewsRepositoryTests {

	@Autowired
	private NewsRepository testedNewsRepo;

	@Test
	public void testFindBySlug() {

		String expected = "title";
		News news = testedNewsRepo.findBySlug("title");
		assertEquals(expected, news.getSlug());
	}

  @Test
	public void testFindBySlugLike() {

		int expected = 6;
		assertEquals(expected, testedNewsRepo.findBySlugLike("title"));
	}
}
