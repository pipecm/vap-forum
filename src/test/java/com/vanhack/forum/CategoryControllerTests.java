package com.vanhack.forum;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.vanhack.forum.controller.CategoryController;
import com.vanhack.forum.dto.Category;
import com.vanhack.forum.service.CategoryService;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
@ContextConfiguration(classes = { TestingRepository.class, TestingSecurity.class })
public class CategoryControllerTests {
	
	@Autowired
	private MockMvc mock;
	
	@MockBean
	private CategoryService service;
	
	
//	@Before
//	public void setUp() {
//		this.mock = MockMvcBuilders
//							.webAppContextSetup(context)
//	                        .apply(springSecurity())
//	                        .build();
//	}
	
	@Test
	public void givenCategories_whenGetCategories_thenReturnJsonArray() throws Exception {
		Category music = new Category("music");
		Category games = new Category("games");
		
		List<Category> categoriesList = new ArrayList<Category>();
		categoriesList.add(music);
		categoriesList.add(games);
		
		given(service.getAllCategories()).willReturn(categoriesList);
		
		mock.perform(get("/api/category")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is(music.getName())));
		

	}
}
