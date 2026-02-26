package com.askElevance.Application.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LLMService {

    @Value("${gemini.api-key}")
    private String apiKey;

    @Value("${gemini.url}")
    private String url;

    public String callLLM(String prompt) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build request body as per Gemini format
        Map<String, Object> textPart = new HashMap<>();
        textPart.put("text", prompt);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(textPart));

        Map<String, Object> body = new HashMap<>();
        body.put("contents", List.of(content));

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        
        String endpoint = url + "/v1/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        ResponseEntity<Map> response =
                restTemplate.postForEntity(endpoint, entity, Map.class);

        // Extract response
        System.out.println(response.getBody());
        Map<String, Object> responseBody = response.getBody();
        List<Map<String, Object>> candidates =
                (List<Map<String, Object>>) responseBody.get("candidates");

        Map<String, Object> firstCandidate = candidates.get(0);
        Map<String, Object> contentMap =
                (Map<String, Object>) firstCandidate.get("content");

        List<Map<String, Object>> parts =
                (List<Map<String, Object>>) contentMap.get("parts");

        return parts.get(0).get("text").toString();
    }
}