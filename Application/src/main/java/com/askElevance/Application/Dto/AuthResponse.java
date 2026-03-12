package com.askElevance.Application.Dto;
	
	public class AuthResponse {
	    private String token;
	    
	    private String name;
	    private String role;
	    
	    public AuthResponse(String token, String username, String role) {
	        this.token = token;
	        this.name = username;
	        this.role = role;
	    }

	    

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

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