package com.vanhack.forum.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.util.ForumConstants.Queries;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	
	public User findById(Long id);
	
	public User findByEmail(String email);
	
	public List<User> findByNicknameContaining(String nickname);
	
	@Query(value = Queries.SQL_USER_CHECK_NICKNAME, nativeQuery = true)
	public User checkNickname(Long id, String nickname);
	
	@Query(value = Queries.SQL_USER_CHECK_EMAIL, nativeQuery = true)
	public User checkEmail(Long id, String email);
	
}
