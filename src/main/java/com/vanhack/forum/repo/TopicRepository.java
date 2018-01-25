package com.vanhack.forum.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vanhack.forum.dto.Category;
import com.vanhack.forum.dto.Topic;
import com.vanhack.forum.dto.User;

public interface TopicRepository extends JpaRepository<Topic, Long> {
	
	public List<Topic> findByTitleContaining(String title);
	
	public List<Topic> findByCategory(Category category);
	
	public List<Topic> findByCreatedBy(User createdBy);
	
	public List<Topic> findByCategoryOrderByLastUpdateDesc(Category category);
	
}
