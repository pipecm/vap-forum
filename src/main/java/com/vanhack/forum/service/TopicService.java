package com.vanhack.forum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vanhack.forum.dao.TopicDAO;
import com.vanhack.forum.dto.Category;
import com.vanhack.forum.dto.Topic;
import com.vanhack.forum.dto.User;

@Service
@Transactional
public class TopicService {

	@Autowired
	private TopicDAO topicDao;
	
	public List<Topic> getAllTopics() {
		return topicDao.findAll();
	}

	public List<Topic> findByTitle(String title) {
		return topicDao.findByTitleContaining(title);
	}

	public List<Topic> findByCategory(Category category) {
		return topicDao.findByCategoryOrderByLastUpdateDesc(category);
	}

	public List<Topic> findByUser(User user) {
		return topicDao.findByCreatedBy(user);
	}

	public Topic addTopic(Topic topic) {
		return topicDao.save(topic);
	}

}
