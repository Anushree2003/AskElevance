package com.askElevance.Application.Dto;


public class KnowledgeBaseDto {

    private String category;
    private String keywords;
    private String staticResponse;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getStaticResponse() {
		return staticResponse;
	}
	public void setStaticResponse(String staticResponse) {
		this.staticResponse = staticResponse;
	}

    // getters and setters
}