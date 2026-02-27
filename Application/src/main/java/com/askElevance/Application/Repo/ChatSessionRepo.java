package com.askElevance.Application.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.askElevance.Application.Entity.ChatSession;
import com.askElevance.Application.Entity.User;

public interface ChatSessionRepo extends JpaRepository<ChatSession, Long>{
	List<ChatSession> findByUser(User user);
	
	List<ChatSession>findByUserOrderByCreatedAtDesc(User user);
}
