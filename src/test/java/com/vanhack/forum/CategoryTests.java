package com.vanhack.forum;

import static org.assertj.core.api.Assertions.assertThat;

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
public class CategoryTests {

	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
    private CategoryDAO categoryDao;
	
	private static Validator validator;
	
	private static final Logger log = LogManager.getLogger(CategoryTests.class);

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
	@Test
	public void whenTableIsEmpty_noCategoriesFound() {
		Iterable<Category> categories = categoryDao.findAll();
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
    public void whenCategoryNameIsEmpty_thenError() {
    	Category empty = new Category("");
    	Set<ConstraintViolation<Category>> violations = validator.validate(empty);
    	for(ConstraintViolation<Category> violation : violations) {
    		log.info(violation.getMessage());
    	}
    	assertThat(violations).isNotEmpty();
    }
    
    @Test
    public void whenCategoryNameHasLessThan5Characters_thenError() {
    	Category shorterThan5 = new Category("Test");
    	Set<ConstraintViolation<Category>> violations = validator.validate(shorterThan5);
    	for(ConstraintViolation<Category> violation : violations) {
    		log.info(violation.getMessage());
    	}
    	assertThat(violations).isNotEmpty();
    }
    
    @Test
    public void whenCategoryNameHasMoreThan20Characters_thenError() {
    	Category longerThan20 = new Category("TestTestTestTestTestTest");
    	Set<ConstraintViolation<Category>> violations = validator.validate(longerThan20);
    	for(ConstraintViolation<Category> violation : violations) {
    		log.info(violation.getMessage());
    	}
    	assertThat(violations).isNotEmpty();
    }
    
    @Test
    public void whenCategoryNameHasBetween5and20Characters_thenOK() {
    	Category validName = new Category("Leisure");
    	Set<ConstraintViolation<Category>> violations = validator.validate(validName);
    	assertThat(violations).isEmpty();
    }
}
