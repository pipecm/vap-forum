package com.vanhack.forum.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vanhack.forum.dto.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	
	static final String SQL_USER_CHECK_NICKNAME = "select * from vap_forum_user "
													+ "where id <> ?1 and nickname = ?2";
	
	static final String SQL_USER_CHECK_EMAIL = "select * from vap_forum_user "
													+ "where id <> ?1 and email = ?2";
	
	public User findById(Long id);
	
	public User findByNickname(String nickname);
	
	public User findByEmail(String email);
	
	public List<User> findByNicknameContaining(String nickname);
	
	public List<User> findByEmailContaining(String email);
	
	@Query(value = SQL_USER_CHECK_NICKNAME, nativeQuery = true)
	public User checkNickname(Long id, String nickname);
	
	@Query(value = SQL_USER_CHECK_EMAIL, nativeQuery = true)
	public User checkEmail(Long id, String email);
	
}
