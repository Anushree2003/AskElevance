package com.askElevance.Application.Dto;
	
	public class AuthResponse {
	    private String token;

	

		public AuthResponse(String string) {
			token = string;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
	    
	    
	}