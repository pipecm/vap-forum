package com.vanhack.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.vanhack.forum.dao.CategoryDAO;
import com.vanhack.forum.dto.Category;

@Controller
public class CategoryController {

	private CategoryDAO categoryDao;

	@Autowired
	public CategoryController(CategoryDAO categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	public Iterable<Category> getAllCategories() {
		return categoryDao.getAllCategories();
	}
	
}
