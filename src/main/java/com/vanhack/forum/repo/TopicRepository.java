package com.vanhack.forum.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vanhack.forum.dto.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
	
	public List<Topic> findByTitleContaining(String title);
	
	//public List<Topic> findByCategory(Long idCategory);
	
	
}
