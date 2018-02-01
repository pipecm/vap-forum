package com.vanhack.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.vanhack.forum.*"})
@EntityScan("com.vanhack.forum.*")
@EnableJpaRepositories("com.vanhack.forum.*")
public class VapForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(VapForumApplication.class, args);
	}
}
