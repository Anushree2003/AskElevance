package com.askElevance.Application.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.askElevance.Application.Dto.MessageDto;
import com.askElevance.Application.Dto.SessionTitleDto;
import com.askElevance.Application.Entity.ChatSession;
import com.askElevance.Application.Entity.KnowledgeBase;
import com.askElevance.Application.Entity.Message;
import com.askElevance.Application.Entity.User;
import com.askElevance.Application.Repo.ChatSessionRepo;
import com.askElevance.Application.Repo.KnowledgeBaseRepo;
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
	

	
	@Autowired
	private KnowledgeBaseService knowledgeBaseService; 

	public String sendMessage(Long sessionId, String message) {

	    ChatSession session = chatSessionRepo.findById(sessionId)
	            .orElseThrow(() -> new RuntimeException("Session not found"));

	    // Save user message
	    Message userMsg = new Message();
	    userMsg.setSession(session);
	    userMsg.setSender("USER");
	    userMsg.setContent(message);
	    userMsg.setTimestamp(LocalDateTime.now());
	    messageRepo.save(userMsg);

	    KnowledgeBase kb = knowledgeBaseService.findCategory(message);

	    String finalResponse;

	    if (kb != null) {

	        String prompt = buildPrompt(message, kb);
	        String llmAnswer = llmService.callLLM(prompt);

	        String intro = "Here is the information regarding " + kb.getCategory() + ":\n\n";

	        String storedResponse = kb.getResponse();	

	        // Check if stored response contains a link
	        boolean containsLink = storedResponse.contains("http://") || storedResponse.contains("https://");

	        if (containsLink) {

	            finalResponse = intro
	                    + llmAnswer
	                    + "\n\nYou can find more details here:\n"
	                    + storedResponse;

	        } else {

	            finalResponse = intro
	                    + llmAnswer
	                    + "\n\nHere's the information you requested\n"
	                    + storedResponse;
	        }
	    }
	    else {
	        finalResponse = "This query is outside the assistant knowledge base. Please contact your Manager or HR.";
	    }

	    // Save assistant message
	    Message aiMsg = new Message();
	    aiMsg.setSession(session);
	    aiMsg.setSender("ASSISTANT");
	    aiMsg.setContent(finalResponse);
	    aiMsg.setTimestamp(LocalDateTime.now());
	    messageRepo.save(aiMsg);
  
	    return finalResponse;
	}
	
	
	
	private String buildPrompt(String question, KnowledgeBase kb) {
	    return """
	You are an internal company assistant.

	Category: %s

	User Question: %s

	Instructions:
	- Start your response with a friendly, easy-to-understand introduction summarizing what the user is asking.
	- I have the reponse stored in databsae and will be returning along with your reply so, provide only the context 
	  like after your reply our reponse will be printed.
	- Keep the tone professional and helpful.
	""".formatted(kb.getCategory(), question);
	}

	public ChatSession createSession(String title,String userEmail) {
		

	    // Fetch the logged-in user from DB
	    User user = userRepo.findByEmail(userEmail);
	    if (user == null) {
	        throw new RuntimeException("User not found with email: " + userEmail);
	    }

	    // Create the chat session
	    ChatSession session = new ChatSession();
	    session.setUser(user);
	    session.setTitle(title != null && !title.isBlank() ? title : "New Chat");
	    session.setCreatedAt(LocalDateTime.now());
		
		return chatSessionRepo.save(session);
		 
	}
	
	public List<ChatSession> getUserSessions(String userEmail) {

		
		 // Fetch the logged-in user from DB
	    User user = userRepo.findByEmail(userEmail);
	    if (user == null) {
	        throw new RuntimeException("User not found with email: " + userEmail);
	    }

	   return chatSessionRepo.findByUserOrderByCreatedAtDesc(user);
	}

	
	public List<MessageDto> getSessionMessages(Long sessionId) {
		
	    ChatSession session = chatSessionRepo.findById(sessionId)
	            .orElseThrow(() -> new RuntimeException("Session not found"));

	   
	    return messageRepo.findBySessionOrderByTimestampAsc(session)
	            .stream()
	            .map(message -> new MessageDto(
	                    message.getContent(),
	                    message.getSender()
	            ))
	            .toList();
	}

	public String deleteSession(Long sessionId) {
		  if (!chatSessionRepo.existsById(sessionId)) {
	            throw new RuntimeException("Session not found with id: " + sessionId);
	        }
  
	        chatSessionRepo.deleteById(sessionId);
	        return "Session deleted successfully";
	}

	public ChatSession updateSessionTitle(Long sessionId, String title) {

	    ChatSession session = chatSessionRepo.findById(sessionId)
	            .orElseThrow(() -> new RuntimeException("Session not found"));

	    session.setTitle(title);

	    return chatSessionRepo.save(session);
	}

}
