package com.vanhack.forum.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vanhack.forum.dto.Category;
import com.vanhack.forum.exception.CategoryException;
import com.vanhack.forum.repo.CategoryRepository;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	private static final Logger log = LogManager.getLogger(CategoryService.class);
	
	public CategoryService() {
		super();
	}
	
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public Category findById(Long id) {
		return categoryRepository.findOne(id);
	}
	
	public Category findByName(String name) {
		return categoryRepository.findByName(name);
	}
	
	public int updateCategory(Category category) throws CategoryException {
		try {
			categoryRepository.save(category);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new CategoryException(1, e.getMessage());
		}
		return 0;
	}
	
	public int deleteCategory(Long id) throws CategoryException {
		try {
			Category category = categoryRepository.findOne(id);
			categoryRepository.delete(category);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new CategoryException(1, e.getMessage());
		}
		return 0;
	}
	
	public boolean isCategoryAvailable(Category category) {
		List<Category> list = categoryRepository.checkName(category.getId(), category.getName());
		if(list.isEmpty() || list == null) {
			return true;
		} else {
			return false;
		}
	}
}
