package com.vanhack.forum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vanhack.forum.dao.TopicDAO;
import com.vanhack.forum.dto.Topic;

@Service
@Transactional
public class TopicService {

	@Autowired
	private TopicDAO topicDao;
	
	public List<Topic> getAllTopics() {
		return topicDao.findAll();
	}

}
