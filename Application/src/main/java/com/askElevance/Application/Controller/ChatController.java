package com.askElevance.Application.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.askElevance.Application.Dto.ChatRequest;
import com.askElevance.Application.Service.ChatService;


@RestController
@RequestMapping("/chat")
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
    
    @GetMapping("/check")
    public String check() {
    	return "server running";
    }
}