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

import java.text.MessageFormat;
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
import com.vanhack.forum.util.CategoryCodes;

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
	
	@Autowired
	private CategoryCodes categoryCodes;
	
	private static final String BASE_URI = "/api/category";
	private static final String USER = "pipecm";
	private static final String PASSWORD = "vanhack";
	private static final String ROLES = "ADMIN";
	
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
		
		mock.perform(get(getUri())
				.with(user(USER).password(PASSWORD).roles(ROLES))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$.responseCode", is(categoryCodes.CATEGORY_NOT_FOUND_CODE)))
				.andExpect(jsonPath("$.responseMessage", is(categoryCodes.CATEGORY_NOT_FOUND_MESSAGE)))
				.andExpect(jsonPath("$.responseContent", is(IsNull.nullValue())));
		
	}
	
	@Test
	public void givenCategories_whenGetCategories_thenReturnJsonArray() throws Exception {
		Category music = new Category("music");
		Category games = new Category("games");
		
		List<Category> categoriesList = Arrays.asList(music, games);
		
		given(service.getAllCategories()).willReturn(categoriesList);
		
		mock.perform(get(getUri())
				.with(user(USER).password(PASSWORD).roles(ROLES))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode", is(categoryCodes.CATEGORY_SUCCESS_CODE)))
				.andExpect(jsonPath("$.responseMessage", is(MessageFormat.format(categoryCodes.CATEGORY_FIND_ALL_SUCCESS_MESSAGE, categoriesList.size()))))
				.andExpect(jsonPath("$.responseContent", hasSize(2)))
				.andExpect(jsonPath("$.responseContent[0].name", is(music.getName())))
				.andExpect(jsonPath("$.responseContent[1].name", is(games.getName())));
		
	}
	
	@Test
	public void whenFindByName_andCategoryExists_thenReturnJsonCategory() throws Exception {
		String name = "random";
		Category random = new Category(name);
		
		given(service.findByName(name)).willReturn(random);
		
		mock.perform(get(getUri(name))
				.with(user(USER).password(PASSWORD).roles(ROLES))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode", is(categoryCodes.CATEGORY_SUCCESS_CODE)))
				.andExpect(jsonPath("$.responseMessage", is(MessageFormat.format(categoryCodes.CATEGORY_FIND_BY_NAME_SUCCESS_MESSAGE, name))))
				.andExpect(jsonPath("$.responseContent", hasSize(1)))
				.andExpect(jsonPath("$.responseContent[0].name", is(random.getName())));
	}
	
	@Test
	public void whenFindByName_andNotExists_thenStatusNotFound() throws Exception {
		String name = "not_found";
		
		given(service.findByName(name)).willReturn(null);
		
		mock.perform(get(getUri(name))
				.with(user(USER).password(PASSWORD).roles(ROLES))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.responseCode", is(categoryCodes.CATEGORY_NAME_NOT_FOUND_CODE)))
				.andExpect(jsonPath("$.responseMessage", is(MessageFormat.format(categoryCodes.CATEGORY_NAME_NOT_FOUND_MESSAGE, name))))
				.andExpect(jsonPath("$.responseContent", is(IsNull.nullValue())));
	}
	
	@Test
	public void whenInsertACorrectCategory_thenOK() throws Exception {
		Category health = new Category("health");
		Category added = new Category();
		added.setId(1L);
		added.setName(health.getName());
		
		given(service.addCategory(health)).willReturn(added);
		
		mock.perform(post(getUri())
				.with(user(USER).password(PASSWORD).roles(ROLES))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(health))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode", is(categoryCodes.CATEGORY_SUCCESS_CODE)))
				.andExpect(jsonPath("$.responseMessage", is(categoryCodes.CATEGORY_INSERT_SUCCESS_MESSAGE)))
				.andExpect(jsonPath("$.responseContent", hasSize(1)))
				.andExpect(jsonPath("$.responseContent[0].name", is(health.getName())));

	}
	
	@Test
	public void whenInsertAnEmptyCategory_thenError() throws Exception {
		testInvalidAttributes(CategoryTestType.EMPTY_NAME);
	}
	
	@Test
	public void whenAddCategoryWithNameShorterThan5Characters_thenError() throws Exception {
		testInvalidAttributes(CategoryTestType.SHORTER_NAME);
	}
	
	@Test
	public void whenAddCategoryWithNameLongerThan20Characters_thenError() throws Exception {
		testInvalidAttributes(CategoryTestType.LONGER_NAME);
	}
	
	@Test
	public void whenAddCategoryWithExistingName_thenError() throws Exception {
		testInvalidAttributes(CategoryTestType.ALREADY_EXISTS);
	}
	
	private void testInvalidAttributes(CategoryTestType testType) throws Exception {
		Category testCategory = getTestCategory();
		int exceptionCode = 0;
		String exceptionMessage = "";
		
		switch(testType) {
			case EMPTY_NAME:
				testCategory.setName("");
				exceptionCode = categoryCodes.CATEGORY_EMPTY_NAME_CODE;
				exceptionMessage = categoryCodes.CATEGORY_EMPTY_NAME_MESSAGE;
				break;
			case SHORTER_NAME:
				testCategory.setName("test");
				exceptionCode = categoryCodes.CATEGORY_VALIDATION_ERROR_CODE;
				exceptionMessage = categoryCodes.CATEGORY_VALIDATION_ERROR_MESSAGE;
				break;	
			case LONGER_NAME:
				testCategory.setName("test_test_test_test_test");
				exceptionCode = categoryCodes.CATEGORY_VALIDATION_ERROR_CODE;
				exceptionMessage = categoryCodes.CATEGORY_VALIDATION_ERROR_MESSAGE;
				break;
			case ALREADY_EXISTS:
				testCategory.setId(1L);
				testCategory.setName("sports");
				exceptionCode = categoryCodes.CATEGORY_NAME_ALREADY_EXISTS_CODE;
				exceptionMessage = categoryCodes.CATEGORY_NAME_ALREADY_EXISTS_MESSAGE;
				break;
			default:
				break;
		}		
		
		given(service.addCategory(testCategory))
			.willThrow(ForumExceptionFactory.create(ExceptionType.CATEGORY_EXCEPTION, 
													exceptionCode, exceptionMessage))
			.willReturn(null);
		
		mock.perform(post(getUri())
				.with(user(USER).password(PASSWORD).roles(ROLES))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testCategory))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.responseCode", is(exceptionCode)))
				.andExpect(jsonPath("$.responseMessage", is(exceptionMessage)))
				.andExpect(jsonPath("$.responseContent", is(IsNull.nullValue())));
		
	}
	
	private Category getTestCategory() {
    	return new Category("testing");
    }
	
	private String getUri() {
		return BASE_URI;
	}
	
	private String getUri(String name) {
		return BASE_URI + "/" + name;
	}

}
