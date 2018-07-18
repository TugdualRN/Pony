package com.pony.repositories;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pony.models.News;

public interface NewsRepository extends JpaRepository<News, Long> {
	
	public News findByTitle(String title);
	public News findBySlug(String slug);
	@Query("SELECT count(t) FROM News t WHERE t.slug LIKE ?1%")
	public int findBySlugLike(String slug);
}
