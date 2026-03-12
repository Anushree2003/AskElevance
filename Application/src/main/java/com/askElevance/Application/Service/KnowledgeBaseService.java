package com.askElevance.Application.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.askElevance.Application.Dto.KnowledgeBaseDto;
import com.askElevance.Application.Entity.KnowledgeBase;
import com.askElevance.Application.Repo.KnowledgeBaseRepo;

@Service
public class KnowledgeBaseService {
	
	@Autowired
	private KnowledgeBaseRepo knowledgeBaseRepo;
	
	
	public KnowledgeBase findCategory(String question) {

	    List<KnowledgeBase> list = knowledgeBaseRepo.findAll();

	    question = question.toLowerCase();

	    for (KnowledgeBase kb : list) {
	    	
	    	if(kb.getKeywords() == null) continue;

	        String[] keys = kb.getKeywords().toLowerCase().split(",");

	        for (String key : keys) {

	            if (question.contains(key.trim())) {
	                return kb;
	            }
	        }
	    }

	    return null;
	}


	public KnowledgeBase saveKnowledge(KnowledgeBaseDto request) {

        KnowledgeBase kb = new KnowledgeBase();
        kb.setCategory(request.getCategory());
        kb.setKeywords(request.getKeywords());
        kb.setResponse(request.getStaticResponse());

        return knowledgeBaseRepo.save(kb);
    }

}
