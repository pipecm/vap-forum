package com.vanhack.forum;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.forum.dto.Category;
import com.vanhack.forum.repo.CategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoryTests {
	
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Test
    public void whenFindByName_thenReturnCategory() {
    	Category music = new Category();
    	music.setId(1L);
    	music.setName("music");
    	categoryRepository.save(music); 	
    	Category found = categoryRepository.findByName("music");
    	assertTrue(found.getName().equals(music.getName()));
    }
}
