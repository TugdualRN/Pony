package com.pony.repositories;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pony.models.News;

public interface NewsRepository extends JpaRepository<News, Long> {
	
	public News findByTitle(String title);
	public News findBySlug(String slug);
}
