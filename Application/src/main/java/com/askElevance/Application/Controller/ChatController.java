package com.askElevance.Application.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.askElevance.Application.Dto.ChatRequest;
import com.askElevance.Application.Dto.MessageDto;
import com.askElevance.Application.Dto.SessionTitleDto;
import com.askElevance.Application.Entity.ChatSession;
import com.askElevance.Application.Entity.Message;
import com.askElevance.Application.Entity.User;
import com.askElevance.Application.Service.ChatService;


@RestController
@RequestMapping("/chat")
@CrossOrigin(
	    origins = "http://localhost:5173",  // frontend URL
	    allowCredentials = "true",          // needed because you send Authorization headers
	    allowedHeaders = "*",               // allow all headers (including Authorization)
	    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS} // allow these methods
	)
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody ChatRequest req) {

        String response = chatService.sendMessage(
                req.getSessionId(),
                req.getMessage()
        );

        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createSession(
            @RequestBody SessionTitleDto sessionTitle,Principal principal
           ) {
    	String email = principal.getName();

        ChatSession session = chatService.createSession(sessionTitle.getTitle(),email);

        return ResponseEntity.ok(session);
    }
    
    @GetMapping("/check")
    public String check() {
    	return "server running";
    }
    
    @GetMapping("/sessions")
    public ResponseEntity<List<SessionTitleDto>> getUserSessions(
    		Principal principal) {
    	
    	String email = principal.getName();

        return ResponseEntity.ok(chatService.getUserSessions(email));
    }
    
    @GetMapping("/messages/{sessionId}")
    public ResponseEntity<List<MessageDto>> getSessionMessages(
            @PathVariable Long sessionId) { 
  
        return ResponseEntity.ok(
                chatService.getSessionMessages(sessionId)
        ); 
    }
}