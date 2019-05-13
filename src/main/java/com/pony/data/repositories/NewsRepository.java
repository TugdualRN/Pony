package com.pony.data.repositories;

import com.pony.entities.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
	
	public News findBySlug(String slug);
	
	@Query("SELECT count(t) FROM News t WHERE t.slug LIKE ?1%")
	public int findBySlugLike(String slug);
	
	public List<News> findByLangOrderByIdDesc(String lang);
}