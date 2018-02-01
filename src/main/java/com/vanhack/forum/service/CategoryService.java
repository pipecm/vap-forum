package com.vanhack.forum.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vanhack.forum.dao.CategoryDAO;
import com.vanhack.forum.dto.Category;
import com.vanhack.forum.exception.CategoryException;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryDAO categoryDao;
	
	private static final Logger log = LogManager.getLogger(CategoryService.class);
	
	public CategoryService() {
		super();
	}
	
	public List<Category> getAllCategories() {
		return categoryDao.findAll();
	}

	public Category findById(Long id) {
		return categoryDao.findOne(id);
	}
	
	public Category findByName(String name) {
		return categoryDao.findByName(name);
	}
	
	public int updateCategory(Category category) throws CategoryException {
		try {
			categoryDao.save(category);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new CategoryException(1, e.getMessage());
		}
		return 0;
	}
	
	public int deleteCategory(Long id) throws CategoryException {
		try {
			Category category = categoryDao.findOne(id);
			categoryDao.delete(category);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new CategoryException(1, e.getMessage());
		}
		return 0;
	}
	
	public boolean isCategoryAvailable(Category category) {
		List<Category> list = categoryDao.checkName(category.getId(), category.getName());
		if(list.isEmpty() || list == null) {
			return true;
		} else {
			return false;
		}
	}
}
