package com.vanhack.forum;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.forum.dao.TopicDAO;
import com.vanhack.forum.dto.Category;
import com.vanhack.forum.dto.Topic;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.service.TopicService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { VapForumApplication.class, TestingRepository.class })
public class TopicServiceTests {
	
	@TestConfiguration
    static class UserServiceTestContextConfiguration {
		@Bean
        public TopicService getService() {
			return new TopicService();
		}
	}
		
	@MockBean
	private TopicDAO topicDao;
	
	@Autowired
	private TopicService service;

	@Before
	public void setUp() {
		User owner = TestObjects.getTestUser();
		Category category = TestObjects.getTestCategory();
		
		Topic firstTopic = TestObjects.getTestTopic();
		firstTopic.setTitle("First Topic");
		firstTopic.setCreatedBy(owner);
		firstTopic.setCategory(category);
		
		Topic secondTopic = TestObjects.getTestTopic();
		secondTopic.setTitle("Second Topic");
		secondTopic.setCreatedBy(owner);
		secondTopic.setCategory(category);
		
		List<Topic> allTopics = Arrays.asList(firstTopic, secondTopic);
		
		Mockito.when(topicDao.findAll())
			.thenReturn(allTopics);
	}
	
	@Test
	public void whenFindAll_thenReturnAllTopics() {
		List<Topic> allTopics = service.getAllTopics();
		assertThat(allTopics).isNotEmpty();
		assertThat(allTopics.size()).isEqualTo(2);
	}
}
