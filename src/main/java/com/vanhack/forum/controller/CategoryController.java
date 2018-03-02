package com.vanhack.forum.controller;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vanhack.forum.controller.ForumResponseFactory.ResponseType;
import com.vanhack.forum.dto.Category;
import com.vanhack.forum.exception.ForumException;
import com.vanhack.forum.service.CategoryService;
import com.vanhack.forum.util.CategoryCodes;
import com.vanhack.forum.util.ForumConstants;
import com.vanhack.forum.util.ForumConstants.CategoryConstants;

@RestController
@RequestMapping(ForumConstants.API_ENDPOINT)
public class CategoryController {
	
	private static final Logger log = LogManager.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping(path = CategoryConstants.CATEGORY_GET_ALL_ENDPOINT)
	public @ResponseBody ResponseEntity<ForumResponse> getAllCategories() {
		ForumResponse response = null; 
		log.info("Fetching all categories");
		List<Category> categories = categoryService.getAllCategories();
		if(categories.isEmpty()) {
			log.info(CategoryCodes.CATEGORY_NOT_FOUND_MESSAGE);
			response = ForumResponseFactory.create(ResponseType.CATEGORY_RESPONSE, 
													CategoryCodes.CATEGORY_NOT_FOUND_CODE, 
													CategoryCodes.CATEGORY_NOT_FOUND_MESSAGE);
			return new ResponseEntity<ForumResponse>(response, HttpStatus.NO_CONTENT);
		}
		log.info(MessageFormat.format(CategoryCodes.CATEGORY_FIND_ALL_SUCCESS_MESSAGE, categories.size()));
		response = ForumResponseFactory.create(ResponseType.CATEGORY_RESPONSE, 
												CategoryCodes.CATEGORY_SUCCESS_CODE, 
												MessageFormat.format(CategoryCodes.CATEGORY_FIND_ALL_SUCCESS_MESSAGE, categories.size()));
		response.setResponseContent(categories);
		return new ResponseEntity<ForumResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = CategoryConstants.CATEGORY_GET_BY_NAME_ENDPOINT)
	public @ResponseBody ResponseEntity<ForumResponse> findCategoriesByName(@RequestParam("name") String name) {
		ForumResponse response = null;
		log.info("Fetching category with name: " + name);
		List<Category> categories = categoryService.findByNameContaining(name);
		if(categories.isEmpty() || categories == null) {
			log.info(MessageFormat.format(CategoryCodes.CATEGORY_NAME_NOT_FOUND_MESSAGE, name));
			response = ForumResponseFactory.create(ResponseType.CATEGORY_RESPONSE, 
													CategoryCodes.CATEGORY_NAME_NOT_FOUND_CODE, 
													MessageFormat.format(CategoryCodes.CATEGORY_NAME_NOT_FOUND_MESSAGE, name));
			return new ResponseEntity<ForumResponse>(response, HttpStatus.NOT_FOUND);
		}
		log.info(MessageFormat.format(CategoryCodes.CATEGORY_FIND_BY_NAME_SUCCESS_MESSAGE, name));
		response = ForumResponseFactory.create(ResponseType.CATEGORY_RESPONSE, 
												CategoryCodes.CATEGORY_SUCCESS_CODE, 
												MessageFormat.format(CategoryCodes.CATEGORY_FIND_BY_NAME_SUCCESS_MESSAGE, name));
		response.setResponseContent(categories);
		return new ResponseEntity<ForumResponse>(response, HttpStatus.OK);
	}
	
	@PostMapping(path = CategoryConstants.CATEGORY_ADD_ENDPOINT)
	public @ResponseBody ResponseEntity<ForumResponse> addCategory(@RequestBody Category category) {
		ForumResponse response = null;
		try {
			Category newCategory = categoryService.addCategory(category);
			if(newCategory != null) {
				response = ForumResponseFactory.create(ResponseType.CATEGORY_RESPONSE, 
															CategoryCodes.CATEGORY_SUCCESS_CODE, 
															CategoryCodes.CATEGORY_INSERT_SUCCESS_MESSAGE);
				response.setResponseContent(Arrays.asList(newCategory));
			}
		} catch(ForumException exception) {
			response = ForumResponseFactory.create(ResponseType.CATEGORY_RESPONSE, exception);
			return new ResponseEntity<ForumResponse>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<ForumResponse>(response, HttpStatus.OK);
	}
	
	@PutMapping(path = CategoryConstants.CATEGORY_UPDATE_ENDPOINT)
	public @ResponseBody ResponseEntity<ForumResponse> updateCategory(@RequestBody Category category) {
		ForumResponse response = null;
		return new ResponseEntity<ForumResponse>(response, HttpStatus.OK);
	}
	
	@DeleteMapping(path = CategoryConstants.CATEGORY_DELETE_ENDPOINT)
	public @ResponseBody ResponseEntity<ForumResponse> deleteCategory(@PathVariable("id") Long id) {
		ForumResponse response = null;
		return new ResponseEntity<ForumResponse>(response, HttpStatus.OK);
	}
}
