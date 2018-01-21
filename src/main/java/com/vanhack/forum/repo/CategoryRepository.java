package com.vanhack.forum.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.vanhack.forum.dto.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
		
	public Category findByName(String name);
	
	@Query(value = "select * from vap_forum_category where id <> ?1 and name = ?2", nativeQuery = true)
	public List<Category> checkName(Long id, String name);
	
}
