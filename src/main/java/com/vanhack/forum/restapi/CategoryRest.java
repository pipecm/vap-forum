package com.vanhack.forum.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanhack.forum.dto.Category;
import com.vanhack.forum.service.CategoryService;

@Controller  
@RequestMapping(path="/api/categories") 
public class CategoryRest {
	
	@Autowired            
	private CategoryService categoryService;

	@GetMapping(path="/allCategories")
	public @ResponseBody Iterable<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}
}
