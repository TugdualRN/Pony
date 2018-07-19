package com.pony.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pony.models.News;

public interface NewsRepository extends JpaRepository<News, Long> {
	
	public News findByTitle(String title);
	public News findBySlug(String slug);

	// @Query("SELECT COUNT(*) FROM t_news AS TN WHERE TN.slug LIKE(:slug%)")
	// public int findBySlugLike(@Param("slug") String slug);
}
