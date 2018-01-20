package com.vanhack.forum.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.vanhack.forum.dto.Category;
import com.vanhack.forum.repo.CategoryRepository;

@Repository
public class CategoryDAO {

	private CategoryRepository repo;

	@Autowired
	public CategoryDAO(CategoryRepository repo) {
		this.repo = repo;
	}
	
	public Iterable<Category> getAllCategories() {
		return repo.findAll();
	}
} 
