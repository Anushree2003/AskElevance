package com.askElevance.Application.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.askElevance.Application.Dto.KnowledgeBaseDto;
import com.askElevance.Application.Entity.KnowledgeBase;
import com.askElevance.Application.Service.KnowledgeBaseService;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeBaseController {

    @Autowired
    private KnowledgeBaseService knowledgeService;

    @PostMapping("/add")
    public ResponseEntity<?> addKnowledge(@RequestBody KnowledgeBaseDto request) {

        KnowledgeBase saved = knowledgeService.saveKnowledge(request);

        return ResponseEntity.ok(saved);
    }
}