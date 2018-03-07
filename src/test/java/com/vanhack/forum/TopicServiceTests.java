package com.vanhack.forum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.forum.dao.PostDAO;
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
	
	@MockBean
	private PostDAO postDao;
	
	@Autowired
	private TopicService service;
	
	@Before
	public void setUp() {
		User owner = TestObjects.getTestUser();
		User myself = TestObjects.getTestUser();
		myself.setId(2L);
		myself.setNickname("pipecm");
		myself.setEmail("pipecm@gmail.com");
		User admin = TestObjects.getTestUser();
		admin.setId(3L);
		admin.setNickname("admin");
		admin.setEmail("admin@vanhack.com");
		
		Category firstCategory = TestObjects.getTestCategory();
		firstCategory.setId(1L);
		firstCategory.setName("First category");
		Category secondCategory = TestObjects.getTestCategory();
		secondCategory.setId(2L);
		secondCategory.setName("Second category");
		Category thirdCategory = TestObjects.getTestCategory();
		thirdCategory.setId(3L);
		thirdCategory.setName("Third category");
		
		Topic firstTopic = TestObjects.getTestTopic();
		firstTopic.setId(1L);
		firstTopic.setTitle("First Topic");
		firstTopic.setCreatedBy(owner);
		firstTopic.setCategory(firstCategory);
		
		Topic secondTopic = TestObjects.getTestTopic();
		secondTopic.setId(2L);
		secondTopic.setTitle("Second Topic");
		secondTopic.setCreatedBy(myself);
		secondTopic.setCategory(secondCategory);
		
		Topic testTopic = TestObjects.getTestTopic();
		testTopic.setId(3L);
		testTopic.setTitle("Test");
		testTopic.setCreatedBy(myself);
		testTopic.setCategory(firstCategory);
		
		List<Topic> emptyList = new ArrayList<Topic>();
		List<Topic> allTopics = Arrays.asList(firstTopic, secondTopic, testTopic);
		List<Topic> foundTopicsByTitle = Arrays.asList(firstTopic, secondTopic);
		List<Topic> foundTopicsByCategory = Arrays.asList(firstTopic, testTopic);
		List<Topic> foundTopicsByUser = Arrays.asList(secondTopic, testTopic);
		
		when(topicDao.findAll()).thenReturn(allTopics);
		when(topicDao.findByTitleContaining("Topic")).thenReturn(foundTopicsByTitle);
		when(topicDao.findByTitleContaining("None")).thenReturn(emptyList);
		when(topicDao.findByCategoryOrderByLastUpdateDesc(firstCategory)).thenReturn(foundTopicsByCategory);
		when(topicDao.findByCategoryOrderByLastUpdateDesc(thirdCategory)).thenReturn(emptyList);
		when(topicDao.findByCreatedBy(myself)).thenReturn(foundTopicsByUser);
		when(topicDao.findByCreatedBy(admin)).thenReturn(emptyList);
	}
	
	@Test
	public void whenFindAll_thenReturnAllTopics() {
		List<Topic> allTopics = service.getAllTopics();
		assertThat(allTopics).isNotEmpty();
		assertThat(allTopics.size()).isEqualTo(3);
	}
	
	@Test
	public void whenFindByTitleContainingText_andTopicsFound_thenReturnTopicsList() {
		String title = "Topic";
		List<Topic> foundTopics = service.findByTitle(title);
		assertThat(foundTopics).isNotEmpty();
		assertThat(foundTopics.size()).isEqualTo(2);
		for(Topic found : foundTopics) {
			assertThat(found.getTitle()).contains(title);
		}
	}
	
	@Test
	public void whenFindByTitleContainingText_andNoTopicsFound_thenReturnEmptyList() {
		String title = "None";
		List<Topic> foundTopics = service.findByTitle(title);
		assertThat(foundTopics).isEmpty();
	}
	
	@Test
	public void whenFindByCategory_andTopicsFound_thenReturnTopicsList() {
		Category myCategory = TestObjects.getTestCategory();
		myCategory.setId(1L);
		myCategory.setName("First category");
		List<Topic> foundTopics = service.findByCategory(myCategory);
		assertThat(foundTopics).isNotEmpty();
		assertThat(foundTopics.size()).isEqualTo(2);
		for(Topic found : foundTopics) {
			assertThat(found.getCategory()).isEqualTo(myCategory);
		}
	}
	
	@Test
	public void whenFindByCategory_andNoTopicsFound_thenReturnEmptyList() {
		Category myCategory = TestObjects.getTestCategory();
		myCategory.setId(3L);
		myCategory.setName("Third category");
		List<Topic> foundTopics = service.findByCategory(myCategory);
		assertThat(foundTopics).isEmpty();
	}
	
	@Test
	public void whenFindByUser_andTopicsFound_thenReturnTopicsList() {
		User myself = TestObjects.getTestUser();
		myself.setId(2L);
		myself.setNickname("pipecm");
		myself.setEmail("pipecm@gmail.com");
		List<Topic> foundTopics = service.findByUser(myself);
		assertThat(foundTopics).isNotEmpty();
		assertThat(foundTopics.size()).isEqualTo(2);
		for(Topic found : foundTopics) {
			assertThat(found.getCreatedBy()).isEqualTo(myself);
		}
	}
	
	@Test
	public void whenFindByUser_andNoTopicsFound_thenReturnEmptyList() {
		User admin = TestObjects.getTestUser();
		admin.setId(3L);
		admin.setNickname("admin");
		admin.setEmail("admin@vanhack.com");
		List<Topic> foundTopics = service.findByUser(admin);
		assertThat(foundTopics).isEmpty();
	}
	
//	@Test
//	public void whenAddANewTopic_thenFirstPostIsCreated() {
//		User owner = TestObjects.getTestUser();
//		Category firstCategory = TestObjects.getTestCategory();
//		firstCategory.setId(1L);
//		firstCategory.setName("First category");
//		Topic newTopic = TestObjects.getTestTopic();
//		newTopic.setTitle("New Topic");
//		newTopic.setCategory(firstCategory);
//		newTopic.setCreatedBy(owner);
//
//		Topic savedTopic = service.addTopic(newTopic);
//		
//		assertThat(savedTopic.getId()).isNotNull();
//		assertThat(savedTopic.getCategory()).isEqualTo(firstCategory);
//		assertThat(savedTopic.getCreatedBy()).isEqualTo(owner);
//	}
}
