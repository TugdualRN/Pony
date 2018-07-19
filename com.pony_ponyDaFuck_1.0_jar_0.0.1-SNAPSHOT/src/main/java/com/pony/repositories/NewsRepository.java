package com.pony.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pony.models.News;

public interface NewsRepository extends JpaRepository<News, Long> {
	
	public News findByTitle(String title);
	public News findBySlug(String slug);
	@Query("SELECT count(t) FROM News t WHERE t.slug LIKE ?1%")
	public int findBySlugLike(String slug);
}
