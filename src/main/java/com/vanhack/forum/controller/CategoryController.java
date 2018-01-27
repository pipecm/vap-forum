package com.vanhack.forum.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vanhack.forum.dto.Category;
import com.vanhack.forum.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
	private static final Logger log = LogManager.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping(path = "/category")
	public ResponseEntity<?> getAllCategories() {
		log.info("Fetching all categories");
		List<Category> categories = categoryService.getAllCategories();
		if(categories.isEmpty()) {
			log.info("No categories found");
			return new ResponseEntity<String>("No categories found", HttpStatus.NO_CONTENT);
		}
		log.info(categories.size() + " categories found");
		return new ResponseEntity<List<Category>>(categories,HttpStatus.OK);
	}
	
}
