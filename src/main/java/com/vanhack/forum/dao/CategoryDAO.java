package com.vanhack.forum.dao;

import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.vanhack.forum.dto.Category;
import com.vanhack.forum.exception.CategoryException;
import com.vanhack.forum.repo.CategoryRepository;

@Repository
public class CategoryDAO {

	private static final Logger log = LogManager.getLogger(CategoryDAO.class);
	
	private CategoryRepository repo;

	@Autowired
	public CategoryDAO(CategoryRepository repo) {
		this.repo = repo;
	}
	
	public int addCategory(Category category) {
		repo.save(category);
		return 0;
	}
	
	public Iterable<Category> getAllCategories() {
		return repo.findAll();
	}
	
	public Category findById(Long id) {
		return repo.findOne(id);
	}
	
	public Category findByName(String name) {
		return repo.findByName(name);
	}
	
	public int updateCategory(Category category) throws CategoryException {
		try {
			repo.save(category);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new CategoryException(1, e.getMessage());
		}
		return 0;
	}
	
	public int deleteCategory(Long id) throws CategoryException {
		try {
			Category category = repo.findOne(id);
			repo.delete(category);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new CategoryException(1, e.getMessage());
		}
		return 0;
	}
	
	public boolean isCategoryAvailable(Category category) {
		List<Category> list = repo.checkName(category.getId(), category.getName());
		if(list.isEmpty() || list == null) {
			return true;
		} else {
			return false;
		}
	}
} 
