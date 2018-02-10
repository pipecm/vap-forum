package com.vanhack.forum;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.forum.dao.CategoryDAO;
import com.vanhack.forum.dto.Category;
import com.vanhack.forum.exception.CategoryException;
import com.vanhack.forum.exception.ForumException;
import com.vanhack.forum.service.CategoryService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { VapForumApplication.class, TestingRepository.class })
public class CategoryServiceTests {
	
	private static final Logger log = LogManager.getLogger(CategoryServiceTests.class);
	
	@TestConfiguration
    static class CategoryServiceTestContextConfiguration {
		@Bean
        public CategoryService getService() {
			return new CategoryService();
		}
	}
	
	@MockBean
	private CategoryDAO categoryDao;
	
	@Autowired
	private CategoryService categoryService;
	
	@Before
	public void setUp() {
		Category sports = new Category("sports");
		Category music = new Category("music");
		
		List<Category> categoryList = Arrays.asList(sports, music);
		
		Mockito.when(categoryDao.findByName(sports.getName()))
			.thenReturn(sports);
		Mockito.when(categoryDao.findAll())
			.thenReturn(categoryList);
		Mockito.when(categoryDao.checkName(1L, "sports"))
			.thenReturn(sports);
	}
	
	@Test
	public void whenFindAll_thenReturnAllCategories() {
		assertThat(categoryService.getAllCategories()).isNotEmpty();
	}
	 
	@Test
	public void whenFindByName_thenReturnCategory() {
		String name = "sports";
		Category found = categoryService.findByName(name);
	    	
		assertThat(found.getName()).isEqualTo(name);
	}
	
	@Test
	public void whenFindingCategoryNotExists_thenError() {
		String name = "leisure";
		Category found = categoryService.findByName(name);
	    	
		assertThat(found).isNull();
	}
	
	@Test
	public void whenAddCategoryWithValidName_thenOK() throws ForumException {
		Category validCategory = new Category("Testing");
		assertThat(categoryService.addCategory(validCategory)).isEqualTo(0);
	}
	
	@Test
	public void whenAddCategoryWithEmptyName_thenExceptionIsThrown() {
		try {
			Category emptyCategory = new Category("");
			categoryService.addCategory(emptyCategory);
		} catch(ForumException thrown) {
			log.error(thrown.getMessage(), thrown);
			assertThat(thrown).isInstanceOf(CategoryException.class);
			assertThat(thrown.getCode()).isEqualTo(201);
		}
		
	}
	
	@Test
	public void whenAddCategoryWithExistingName_thenExceptionIsThrown() {
		try {
			Category existingCategory = new Category();
			existingCategory.setId(1L);
			existingCategory.setName("sports");
			categoryService.addCategory(existingCategory);
		} catch(ForumException thrown) {
			log.error(thrown.getMessage(), thrown);
			assertThat(thrown).isInstanceOf(CategoryException.class);
			assertThat(thrown.getCode()).isEqualTo(202);
		}
	}
	
	@Test
	public void whenAddCategoryWithNameShorterThan5Characters_thenExceptionIsThrown() {
		try {
			Category shorterName = new Category("Test");
			categoryService.addCategory(shorterName);
		} catch(ForumException thrown) {
			log.error(thrown.getMessage(), thrown);
			assertThat(thrown).isInstanceOf(CategoryException.class);
			assertThat(thrown.getCode()).isEqualTo(203);
		}
	}
	
	@Test
	public void whenAddCategoryWithNameLongerThan20Characters_thenExceptionIsThrown() {
		try {
			Category longerName = new Category("Test_test_test_test_test");
			categoryService.addCategory(longerName);
		} catch(ForumException thrown) {
			log.error(thrown.getMessage(), thrown);
			assertThat(thrown).isInstanceOf(CategoryException.class);
			assertThat(thrown.getCode()).isEqualTo(203);
		}
	}
}
