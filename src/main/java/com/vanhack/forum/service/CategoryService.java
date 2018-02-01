package com.vanhack.forum.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vanhack.forum.dao.CategoryDAO;
import com.vanhack.forum.dto.Category;
import com.vanhack.forum.exception.ForumException;
import com.vanhack.forum.exception.ForumExceptionFactory;
import com.vanhack.forum.util.CategoryCodes;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryDAO categoryDao;
	
	@Autowired
	private CategoryCodes categoryCodes;
	
	private static final Logger log = LogManager.getLogger(CategoryService.class);
	
	public CategoryService() {
		
	}
	
	public int addCategory(Category category) throws ForumException {
		if(validateCategory(category)) {
			try {
				categoryDao.save(category);
			} catch(Exception cause) {
				throwCategoryException(categoryCodes.CATEGORY_UNEXPECTED_ERROR_CODE,
										categoryCodes.CATEGORY_UNEXPECTED_ERROR_MESSAGE,
										cause);
			}
		}
		
		return categoryCodes.CATEGORY_SUCCESS_CODE;
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
	
	public int updateCategory(Category category) throws ForumException {
		if(validateCategory(category)) {
			try {
				categoryDao.save(category);
			} catch(Exception cause) {
				throwCategoryException(categoryCodes.CATEGORY_UNEXPECTED_ERROR_CODE,
										categoryCodes.CATEGORY_UNEXPECTED_ERROR_MESSAGE,
										cause);
			}
		}
		
		return categoryCodes.CATEGORY_SUCCESS_CODE;
	}
	
	public int deleteCategory(Long id) throws ForumException {
		try {
			Category category = categoryDao.findOne(id);
			categoryDao.delete(category);
		} catch(Exception cause) {
			log.error(cause.getMessage(), cause);
			throwCategoryException(categoryCodes.CATEGORY_UNEXPECTED_ERROR_CODE,
									categoryCodes.CATEGORY_UNEXPECTED_ERROR_MESSAGE,
									cause);
		}
		return categoryCodes.CATEGORY_SUCCESS_CODE;
	}
	
	public boolean isCategoryAvailable(Category category) {
		List<Category> list = categoryDao.checkName(category.getId(), category.getName());
		if(list.isEmpty() || list == null) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean validateCategory(Category category) throws ForumException {
		if(category.getName() == null || category.getName().equals("")) {
			throwCategoryException(categoryCodes.CATEGORY_EMPTY_NAME_CODE,
									categoryCodes.CATEGORY_EMPTY_NAME_MESSAGE);
		} else if(!isNameAvailable(category)) {
			throwCategoryException(categoryCodes.CATEGORY_NAME_ALREADY_EXISTS_CODE,
									categoryCodes.CATEGORY_NAME_ALREADY_EXISTS_MESSAGE);
		}
		return true;
	}
	
	public boolean isNameAvailable(Category category) {;
		if(categoryDao.checkName(category.getId(), category.getName()) == null) {
			return true;
		} else {
			return false;
		}
	}
	
	private void throwCategoryException(int code, String message) throws ForumException {
		throw ForumExceptionFactory.create(ForumExceptionFactory.CATEGORY_EXCEPTION, code, message);
	}
	
	private void throwCategoryException(int code, String message, Throwable cause) throws ForumException {
		ForumException exception = ForumExceptionFactory.create(ForumExceptionFactory.CATEGORY_EXCEPTION, code, message);
		exception.initCause(cause);
		throw exception;
	}
}
