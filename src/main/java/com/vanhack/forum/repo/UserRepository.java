package com.vanhack.forum.repo;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.vanhack.forum.dto.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public List<User> findByNickname(String nickname);
	
	public List<User> findByEmail(String email);
	
}
