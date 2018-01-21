package com.vanhack.forum.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.vanhack.forum.dto.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public List<User> findByNickname(String nickname);
	
	public List<User> findByEmail(String email);
	
	@Query(value = "select * from vap_forum_user where id <> ?1 and nickname = ?2", nativeQuery = true)
	public List<User> checkNickname(Long id, String nickname);
	
	@Query(value = "select * from vap_forum_user where id <> ?1 and email = ?2", nativeQuery = true)
	public List<User> checkEmail(Long id, String email);
	
}
