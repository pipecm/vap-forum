package com.vanhack.forum.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vanhack.forum.dto.Category;
import com.vanhack.forum.dto.Topic;
import com.vanhack.forum.dto.User;

@Repository
public interface TopicDAO extends JpaRepository<Topic, Long> {
	
public List<Topic> findByTitleContaining(String title);
	
	public List<Topic> findByCategory(Category category);
	
	public List<Topic> findByCreatedBy(User createdBy);
	
	public List<Topic> findByCategoryOrderByLastUpdateDesc(Category category);
	
}
