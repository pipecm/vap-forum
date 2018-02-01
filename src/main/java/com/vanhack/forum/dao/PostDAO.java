package com.vanhack.forum.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vanhack.forum.dto.Post;
import com.vanhack.forum.dto.Topic;

@Repository
public interface PostDAO extends JpaRepository<Post, Long> {
	
	public List<Post> findByTopicOrderByCreationDateAsc(Topic topic);

}
