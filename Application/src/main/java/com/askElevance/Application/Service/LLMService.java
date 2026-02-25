package com.askElevance.Application.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LLMService {

//    @Value("${watsonx.api-key}")
    private String apiKey;

//    @Value("${watsonx.project-id}")
    private String projectId;

//    @Value("${watsonx.url}")
    private String url;

    public String callLLM(String prompt) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("input", prompt);
        body.put("project_id", projectId);
        body.put("model_id", "ibm/granite-13b-chat-v2");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("max_new_tokens", 300);
        parameters.put("temperature", 0.7);

        body.put("parameters", parameters);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        url + "/ml/v1/text/generation?version=2023-05-29",
                        entity,
                        Map.class
                );

        List<Map<String, Object>> results =
                (List<Map<String, Object>>) response.getBody().get("results");

        return results.get(0).get("generated_text").toString();
    }
}
