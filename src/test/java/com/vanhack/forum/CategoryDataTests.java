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
	
	private static final int CATEGORY_OK 	= 0;
	private static final int EMPTY_NAME 	= 1;
	private static final int SHORTER_NAME 	= 2;
	private static final int LONGER_NAME 	= 3;

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
		categoryDao.save(games);
		
		assertThat(games).hasFieldOrPropertyWithValue("name", "games");
	}
	
    @Test
    public void whenFindByName_thenReturnCategory() {
    	Category music = new Category("music");
    	entityManager.persist(music);
    	entityManager.flush();

    	Category found = categoryDao.findByName(music.getName());
    	
    	assertThat(found.getName()).isEqualTo(music.getName());
    }
    
    @Test
    public void whenCategoryNameHasBetween5and20Characters_thenOK() {
    	testInvalidAttributes(CATEGORY_OK);
    }
    
    @Test
    public void whenCategoryNameIsEmpty_thenError() {
    	testInvalidAttributes(EMPTY_NAME);
    }
    
    @Test
    public void whenCategoryNameHasLessThan5Characters_thenError() {
    	testInvalidAttributes(SHORTER_NAME);
    }
    
    @Test
    public void whenCategoryNameHasMoreThan20Characters_thenError() {
    	testInvalidAttributes(LONGER_NAME);
    }
    
    private Category getTestCategory() {
    	return new Category("Testing");
    }
    
    private void testInvalidAttributes(int attribute) {
    	Category testCategory = getTestCategory();
    	
    	switch(attribute) {
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
    	
    	if(attribute == CATEGORY_OK) {
    		assertThat(violations).isEmpty();
    	} else {
    		assertThat(violations).isNotEmpty();
    	}
    }
}
