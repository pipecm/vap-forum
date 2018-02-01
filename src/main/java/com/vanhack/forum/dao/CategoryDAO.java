package com.vanhack.forum.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vanhack.forum.dto.Category;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Long> {
	
	static final String SQL_CATEGORY_CHECK_NAME = "select * from vap_forum_category "
													+ "where id <> ?1 and name = ?2";
	
	public Category findByName(String name);
	
	@Query(value = SQL_CATEGORY_CHECK_NAME, nativeQuery = true)
	public List<Category> checkName(Long id, String name);
}
