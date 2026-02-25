package com.askElevance.Application.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.askElevance.Application.Entity.ChatSession;
import com.askElevance.Application.Entity.Message;
import com.askElevance.Application.Entity.User;
import com.askElevance.Application.Repo.ChatSessionRepo;
import com.askElevance.Application.Repo.MessageRepo;
import com.askElevance.Application.Repo.UserRepo;

@Service
public class ChatService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ChatSessionRepo chatSessionRepo;
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private LLMService llmService;

	public String sendMessage(Long sessionId, String message) {
	
		ChatSession session = chatSessionRepo.findById(sessionId).orElseThrow();

        // Save user message
        Message userMsg = new Message();
        userMsg.setSession(session);
        userMsg.setSender("USER");
        userMsg.setContent(message);
        userMsg.setTimestamp(LocalDateTime.now());
        messageRepo.save(userMsg);

        // Get history
        List<Message> history =
                messageRepo.findBySessionOrderByTimestampAsc(session);
        
        String prompt = buildPrompt(history);
//
//        String response = llmService.callLLM(prompt);

        // Save assistant message
        Message aiMsg = new Message();
        aiMsg.setSession(session);
        aiMsg.setSender("ASSISTANT");
//        aiMsg.setContent(response);
        aiMsg.setTimestamp(LocalDateTime.now());
        messageRepo.save(aiMsg);

//        return response;
        return prompt;
	}
	
	private String buildPrompt(List<Message> messages) {

        StringBuilder sb = new StringBuilder();
        sb.append("You are an onboarding assistant for Elevance and IBM.\n");

        for (Message m : messages) {
            sb.append(m.getSender())
              .append(": ")
              .append(m.getContent())
              .append("\n");
        }

        return sb.toString();
    }

	public ChatSession createSession(String title) {
		ChatSession session = new ChatSession();
		User user = new User();
		user.setName("Arohi");
		userRepo.save(user);
	    session.setUser(user);
	    session.setTitle(title != null && !title.isBlank() ? title : "New Chat");
	    session.setCreatedAt(LocalDateTime.now());
		
		return chatSessionRepo.save(session);
		
	}
	
	

}
