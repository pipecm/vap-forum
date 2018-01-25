package com.vanhack.forum.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vanhack.forum.dto.Post;
import com.vanhack.forum.dto.Topic;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	public List<Post> findByTopicOrderByCreationDateAsc(Topic topic);

}
