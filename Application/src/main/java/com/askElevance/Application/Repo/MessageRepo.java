package com.askElevance.Application.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.askElevance.Application.Entity.ChatSession;
import com.askElevance.Application.Entity.Message;

@EnableJpaRepositories
public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findBySessionOrderByTimestampAsc(ChatSession session);
}
