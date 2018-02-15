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
		
		Category testing = new Category();
		testing.setId(1L);
		testing.setName("testing");
		
		List<Category> categoryList = Arrays.asList(sports, music);
		
		Mockito.when(categoryDao.findByName(sports.getName()))
			.thenReturn(sports);
		Mockito.when(categoryDao.findAll())
			.thenReturn(categoryList);
		Mockito.when(categoryDao.checkName(1L, "sports"))
			.thenReturn(sports);
		Mockito.when(categoryDao.save(new Category("testing")))
			.thenReturn(testing);
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
		testInvalidAttributes(CategoryTestType.CATEGORY_OK);
	}
	
	@Test
	public void whenAddCategoryWithEmptyName_thenExceptionIsThrown() {
		testInvalidAttributes(CategoryTestType.EMPTY_NAME);	
	}
	
	@Test
	public void whenAddCategoryWithNameShorterThan5Characters_thenExceptionIsThrown() {
		testInvalidAttributes(CategoryTestType.SHORTER_NAME);
	}
	
	@Test
	public void whenAddCategoryWithNameLongerThan20Characters_thenExceptionIsThrown() {
		testInvalidAttributes(CategoryTestType.LONGER_NAME);
	}
	
	@Test
	public void whenAddCategoryWithExistingName_thenExceptionIsThrown() {
		testInvalidAttributes(CategoryTestType.ALREADY_EXISTS);
	}
	
	private void testInvalidAttributes(CategoryTestType testType) {
		Category testCategory = getTestCategory();
		Category newCategory = null;
		int exceptionCode = 0;
    	
    	switch(testType) {
    		case EMPTY_NAME:
    			testCategory.setName("");
    			exceptionCode = 201;
    			break;
    		case SHORTER_NAME:
    			testCategory.setName("test");
    			exceptionCode = 203;
    			break;	
    		case LONGER_NAME:
    			testCategory.setName("test_test_test_test_test");
    			exceptionCode = 203;
    			break;
    		case ALREADY_EXISTS:
    			testCategory.setId(1L);
    			testCategory.setName("sports");
    			exceptionCode = 202;
    			break;
    		default:
    			break;
    	}		
    	
    	try {
    		newCategory = categoryService.addCategory(testCategory);
		} catch(ForumException thrown) {
			log.error(thrown.getMessage());
			assertThat(thrown).isInstanceOf(CategoryException.class);
			assertThat(thrown.getCode()).isEqualTo(exceptionCode);
			assertThat(newCategory).isNull();
		}
    	
    	if(testType == CategoryTestType.CATEGORY_OK) {
	    	assertThat(newCategory).isNotNull();
	    	assertThat(newCategory.getId()).isNotNull();
	    	assertThat(newCategory.getName()).isEqualTo(testCategory.getName());
    	}

	}
	
	private Category getTestCategory() {
    	return new Category("testing");
    }
}
