package com.vanhack.forum;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanhack.forum.controller.CategoryController;
import com.vanhack.forum.dto.Category;
import com.vanhack.forum.exception.ForumExceptionFactory;
import com.vanhack.forum.exception.ForumExceptionFactory.ExceptionType;
import com.vanhack.forum.service.CategoryService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { VapForumApplication.class, TestingRepository.class })
@WebMvcTest(CategoryController.class)
@WebAppConfiguration
public class CategoryControllerTests {
	
	@Autowired
    private WebApplicationContext context;
	
	private MockMvc mock;

	@MockBean
	private CategoryService service;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Before
    public void setUp() {
    	mock = MockMvcBuilders
                	.webAppContextSetup(context)
                	.apply(springSecurity())
                	.build();
    }
	
	@Test
	public void givenNoCategories_whenGetCategories_thenStatusNoContent() throws Exception {
		List<Category> emptyList = new ArrayList<Category>();
		
		given(service.getAllCategories()).willReturn(emptyList);
		
		mock.perform(get("/api/category")
				.with(user("pipecm").password("vanhack").roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$.responseCode", is(204)))
				.andExpect(jsonPath("$.responseContent", is(IsNull.nullValue())));
		
	}
	
	@Test
	public void givenCategories_whenGetCategories_thenReturnJsonArray() throws Exception {
		Category music = new Category("music");
		Category games = new Category("games");
		
		List<Category> categoriesList = Arrays.asList(music, games);
		
		given(service.getAllCategories()).willReturn(categoriesList);
		
		mock.perform(get("/api/category")
				.with(user("pipecm").password("vanhack").roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode", is(0)))
				.andExpect(jsonPath("$.responseContent", hasSize(2)))
				.andExpect(jsonPath("$.responseContent[0].name", is(music.getName())))
				.andExpect(jsonPath("$.responseContent[1].name", is(games.getName())));
		
	}
	
	@Test
	public void whenFindByName_andCategoryExists_thenReturnJsonCategory() throws Exception {
		String name = "random";
		Category random = new Category(name);
		
		given(service.findByName(name)).willReturn(random);
		
		mock.perform(get("/api/category/" + name)
				.with(user("pipecm").password("vanhack").roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode", is(0)))
				.andExpect(jsonPath("$.responseContent", hasSize(1)))
				.andExpect(jsonPath("$.responseContent[0].name", is(random.getName())));
	}
	
	@Test
	public void whenFindByName_andNotExists_thenStatusNotFound() throws Exception {
		String name = "not_found";
		
		given(service.findByName(name)).willReturn(null);
		
		mock.perform(get("/api/category/" + name)
				.with(user("pipecm").password("vanhack").roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.responseCode", is(205)))
				.andExpect(jsonPath("$.responseContent", is(IsNull.nullValue())));
	}
	
	@Test
	public void whenInsertAnEmptyCategory_thenError() throws Exception {
		Category empty = new Category("");
		
		given(service.addCategory(empty))
			.willThrow(ForumExceptionFactory.create(ExceptionType.CATEGORY_EXCEPTION, 201, "Bad request"))
			.willReturn(null);
		
		mock.perform(post("/api/category")
				.with(user("pipecm").password("vanhack").roles("ADMIN"))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(empty))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.responseCode", is(201)))
				.andExpect(jsonPath("$.responseContent", is(IsNull.nullValue())));
		
	}
	
	@Test
	public void whenInsertACorrectCategory_thenOK() throws Exception {
		Category health = new Category("health");
		Category added = new Category();
		added.setId(1L);
		added.setName(health.getName());
		
		given(service.addCategory(health)).willReturn(added);
		
		mock.perform(post("/api/category")
				.with(user("pipecm").password("vanhack").roles("ADMIN"))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(health))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode", is(0)))
				.andExpect(jsonPath("$.responseContent", hasSize(1)))
				.andExpect(jsonPath("$.responseContent[0].name", is(health.getName())));

	}
}
