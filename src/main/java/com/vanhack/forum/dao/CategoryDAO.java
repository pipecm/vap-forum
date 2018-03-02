package com.vanhack.forum.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vanhack.forum.util.ForumConstants.Queries;

import com.vanhack.forum.dto.Category;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Long> {
	
	public List<Category> findByNameContaining(String name);
	
	@Query(value = Queries.SQL_CATEGORY_CHECK_NAME, nativeQuery = true)
	public Category checkName(Long id, String name);
}
