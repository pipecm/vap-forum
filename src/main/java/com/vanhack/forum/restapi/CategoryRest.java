package com.vanhack.forum.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanhack.forum.controller.CategoryController;
import com.vanhack.forum.dto.Category;

@Controller  
@RequestMapping(path="/api/categories") 
public class CategoryRest {
	
	@Autowired            
	private CategoryController categoryController;

	@GetMapping(path="/allCategories")
	public @ResponseBody Iterable<Category> getAllCategories() {
		return categoryController.getAllCategories();
	}
}
