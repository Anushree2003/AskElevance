package com.askElevance.Application.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.askElevance.Application.Entity.KnowledgeBase;

public interface KnowledgeBaseRepo extends JpaRepository<KnowledgeBase, Long> {
    List<KnowledgeBase> findAll();
}