package com.askElevance.Application.Dto;



import lombok.Data;
import java.util.List;

@Data
public class ChatResponse {
	
    private String answer;
    private List<String> sourcesUsed; // e.g., list of article titles
}

