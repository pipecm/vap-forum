package com.vanhack.forum;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.forum.dto.Category;
import com.vanhack.forum.dao.CategoryDAO;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryDataTests {
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
    private CategoryDAO categoryDao;
	
	private static Validator validator;
	
	private static final Logger log = LogManager.getLogger(CategoryDataTests.class);

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
	@Test
	public void whenTableIsEmpty_noCategoriesFound() {
		List<Category> categories = categoryDao.findAll();
		assertThat(categories).isEmpty();
	}
	
	@Test
	public void whenCategoryIsSaved_itMustBeRecordedInDB() {
		Category games = new Category("games");
		Category savedCategory = categoryDao.save(games);
		
		assertThat(savedCategory.getId()).isNotNull();
		assertThat(savedCategory).hasFieldOrPropertyWithValue("name", games.getName());
	}
	
    @Test
    public void whenFindByName_andCategoriesFound_thenReturnCategoriesList() {
    	String name = "music";
    	Category rock = new Category("rock music");
    	Category classic = new Category("classic music");
    	entityManager.persist(rock);
    	entityManager.persist(classic);
    	entityManager.flush();

    	List<Category> categoriesFound = categoryDao.findByNameContaining(name);
    	
    	assertThat(categoriesFound).isNotEmpty();
    	assertThat(categoriesFound.size()).isEqualTo(2);
    	for(Category found : categoriesFound) {
			assertThat(found.getName()).contains(name);
		}
    }
    
    @Test
    public void whenFindByName_andCategoriesNotFound_thenReturnEmptyList() {
    	String name = "history";
    	List<Category> categoriesFound = categoryDao.findByNameContaining(name);
    	assertThat(categoriesFound).isEmpty();
    }
    
    @Test
    public void whenCategoryNameHasBetween5and20Characters_thenOK() {
    	testInvalidAttributes(CategoryTestType.CATEGORY_OK);
    }
    
    @Test
    public void whenCategoryNameIsEmpty_thenError() {
    	testInvalidAttributes(CategoryTestType.EMPTY_NAME);
    }
    
    @Test
    public void whenCategoryNameHasLessThan5Characters_thenError() {
    	testInvalidAttributes(CategoryTestType.SHORTER_NAME);
    }
    
    @Test
    public void whenCategoryNameHasMoreThan20Characters_thenError() {
    	testInvalidAttributes(CategoryTestType.LONGER_NAME);
    }
    
    private Category getTestCategory() {
    	return new Category("Testing");
    }
    
    private void testInvalidAttributes(CategoryTestType testType) {
    	Category testCategory = getTestCategory();
    	
    	switch(testType) {
    		case EMPTY_NAME:
    			testCategory.setName("");
    			break;
    		case SHORTER_NAME:
    			testCategory.setName("test");
    			break;	
    		case LONGER_NAME:
    			testCategory.setName("test_test_test_test_test");
    			break;
    		default:
    			break;
    	}		
    	
    	Set<ConstraintViolation<Category>> violations = validator.validate(testCategory);
    	for(ConstraintViolation<Category> violation : violations) {
    		log.info(violation.getMessage());
    	}
    	
    	if(testType == CategoryTestType.CATEGORY_OK) {
    		assertThat(violations).isEmpty();
    	} else {
    		assertThat(violations).isNotEmpty();
    	}
    }
}
