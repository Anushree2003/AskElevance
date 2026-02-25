package com.askElevance.Application.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CHAT_SESSION")
public class ChatSession {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    public Long getId() {
		return id;
	}

	 public void setId(Long id) {
		 this.id = id;
	 }

	 public String getTitle() {
		 return title;
	 }

	 public void setTitle(String title) {
		 this.title = title;
	 }

	 public User getUser() {
		 return user;
	 }

	 public void setUser(User user) {
		 this.user = user;
	 }

	 public LocalDateTime getCreatedAt() {
		 return createdAt;
	 }

	 public void setCreatedAt(LocalDateTime createdAt) {
		 this.createdAt = createdAt;
	 }

		private String title;

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;

	    private LocalDateTime createdAt;

}