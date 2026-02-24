package com.askElevance.Application.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.askElevance.Application.Entity.User;

@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User, Long> {
	User findByIdIgnoreCase(Long id);
}
