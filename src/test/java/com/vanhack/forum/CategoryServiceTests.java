package com.vanhack.forum;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.forum.dao.CategoryDAO;
import com.vanhack.forum.dto.Category;
import com.vanhack.forum.service.CategoryService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryServiceTests {
	
	@TestConfiguration
    static class CategoryServiceTestContextConfiguration {
		@Bean
        public CategoryService getController() {
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
		Mockito.when(categoryDao.findByName(sports.getName()))
			.thenReturn(sports);
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
}
