package com.vanhack.forum;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.forum.dao.TopicDAO;
import com.vanhack.forum.dto.Category;
import com.vanhack.forum.dto.Post;
import com.vanhack.forum.dto.Topic;
import com.vanhack.forum.dto.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TopicDataTests {

	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private TopicDAO topicDao;
	
	private static final Logger log = LogManager.getLogger(TopicDataTests.class);
	
	@Test
	public void whenTableIsEmpty_noTopicsFound() {
		List<Topic> topics = topicDao.findAll();
		assertThat(topics).isEmpty();
	}
	
	@Test
	public void whenTableIsNotEmpty_thenReturnAllTopics() {
		testFindBy(TopicTestType.FIND_ALL);
	}
	
	@Test
	public void whenFindByTitle_thenReturnTopicList() {
		testFindBy(TopicTestType.FIND_BY_TITLE);
	}
	
	@Test
	public void whenFindByCategory_thenReturnTopicList() {
		testFindBy(TopicTestType.FIND_BY_CATEGORY);
	}
	
	@Test
	public void whenFindByUser_thenReturnCreatedByUserTopicList() {
		testFindBy(TopicTestType.FIND_BY_USER);
	}
	
	@Test
	public void whenTopicIsSaved_itMustBeRecordedInDB() {
		User owner = TestObjects.getTestUser();
		Category category = TestObjects.getTestCategory();
		Topic testTopic = TestObjects.getTestTopic();
		Post firstPost = TestObjects.getTestPost();
		
		String title = "My Topic";
		testTopic.setTitle(title);
		testTopic.setCreatedBy(owner);
		testTopic.setCategory(category);
		testTopic.setPosts(Arrays.asList(firstPost));
		
		Topic savedTopic = topicDao.save(testTopic);
		
		assertThat(savedTopic.getId()).isNotNull();
		assertThat(savedTopic).hasFieldOrPropertyWithValue("title", title);
		assertThat(savedTopic).hasFieldOrPropertyWithValue("createdBy", owner);
		assertThat(savedTopic).hasFieldOrPropertyWithValue("category", category);
	}
	
	@Test
	public void whenTopicIsUpdated_itMustBeRecordedInDB() {
		User owner = TestObjects.getTestUser();
		Category category = TestObjects.getTestCategory();
		Topic testTopic = TestObjects.getTestTopic();
			
		String title = "My Topic";
		testTopic.setTitle(title);
		testTopic.setCreatedBy(owner);
		testTopic.setCategory(category);
		
		Topic savedTopic = topicDao.save(testTopic);
		log.debug(savedTopic);
	
		assertThat(savedTopic.getId()).isNotNull();
		assertThat(savedTopic).hasFieldOrPropertyWithValue("title", title);
		assertThat(savedTopic).hasFieldOrPropertyWithValue("createdBy", owner);
		assertThat(savedTopic).hasFieldOrPropertyWithValue("category", category);
		
		title = "Updated Topic";
		Date now = new Date();
		savedTopic.setTitle(title);
		savedTopic.setLastUpdate(now);
		
		Topic updatedTopic = topicDao.save(savedTopic);
		
		assertThat(updatedTopic.getId()).isEqualTo(savedTopic.getId());
		assertThat(updatedTopic).hasFieldOrPropertyWithValue("title", title);
		assertThat(updatedTopic).hasFieldOrPropertyWithValue("lastUpdate", now);
	}
	
	@Test
	public void whenTopicIsDeleted_itMustBeDeletedFromDB() {
		User owner = TestObjects.getTestUser();
		Category category = TestObjects.getTestCategory();
		Topic testTopic = TestObjects.getTestTopic();
			
		String title = "My Topic";
		testTopic.setTitle(title);
		testTopic.setCreatedBy(owner);
		testTopic.setCategory(category);
		
		Topic savedTopic = topicDao.save(testTopic);
		log.debug(savedTopic);
	
		assertThat(savedTopic.getId()).isNotNull();
		assertThat(savedTopic).hasFieldOrPropertyWithValue("title", title);
		assertThat(savedTopic).hasFieldOrPropertyWithValue("createdBy", owner);
		assertThat(savedTopic).hasFieldOrPropertyWithValue("category", category);
		
		Long deletedId = savedTopic.getId();
		topicDao.delete(deletedId);
		assertThat(topicDao.findById(deletedId)).isNull();
		
	}

	private void testFindBy(TopicTestType testType) {
		User owner = TestObjects.getTestUser();
		Category category = TestObjects.getTestCategory();
		String keyword = "Topic";
		
		Topic firstTopic = TestObjects.getTestTopic();
		firstTopic.setTitle("First Topic");
		firstTopic.setCreatedBy(owner);
		firstTopic.setCategory(category);
		
		Topic secondTopic = TestObjects.getTestTopic();
		secondTopic.setTitle("Second Topic");
		secondTopic.setCreatedBy(owner);
		secondTopic.setCategory(category);
		
		entityManager.persist(owner);
		entityManager.persist(category);
		entityManager.persist(firstTopic);
		entityManager.persist(secondTopic);
		entityManager.flush();
		
		List<Topic> topicsFound = null;
		
		switch(testType) {
			case FIND_ALL:
				topicsFound = topicDao.findAll();
				assertList(topicsFound, 2);
				break;
			case FIND_BY_TITLE:
				topicsFound = topicDao.findByTitleContaining(keyword);
				assertList(topicsFound, 2);
				for(Topic found : topicsFound) {
					assertThat(found.getTitle()).contains(keyword);
				}
				break;
			case FIND_BY_CATEGORY:
				topicsFound = topicDao.findByCategoryOrderByLastUpdateDesc(category);
				assertList(topicsFound, 2);
				for(Topic found : topicsFound) {
					assertThat(found.getCategory()).isEqualTo(category);
				}
				break;
			case FIND_BY_USER:
				topicsFound = topicDao.findByCreatedBy(owner);
				assertList(topicsFound, 2);
				for(Topic found : topicsFound) {
					assertThat(found.getCreatedBy()).isEqualTo(owner);
				}
				break;
			default:
				break;
		}	
	}
	
	private void assertList(List<Topic> list, int size) {
		assertThat(list).isNotEmpty();
		assertThat(list.size()).isEqualTo(size);
	}
	
}
	
