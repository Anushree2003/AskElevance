package com.askElevance.Application.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.askElevance.Application.Dto.AuthResponse;
import com.askElevance.Application.Dto.LoginRequest;
import com.askElevance.Application.Dto.RegisterRequest;
import com.askElevance.Application.Service.AuthService;

//import com.elevance.demo.dto.AuthResponse;
//import com.elevance.demo.dto.RegisterRequest;
//import com.elevance.demo.service.AuthService;



@RestController
@RequestMapping("/auth")

public class AuthController {
	 @Autowired
	    private AuthService authService;

	    // Register API
	    @PostMapping("/register")
	    public ResponseEntity<String> register(
	            @RequestBody RegisterRequest request) {

	        authService.register(request);
	        return ResponseEntity.ok("User Registered Successfully");
	    }
	    

	    // Login API
	    @PostMapping("/login")
	    public ResponseEntity<AuthResponse> login(
	            @RequestBody LoginRequest request) {

	        AuthResponse response =
	                authService.login(request);

	        return ResponseEntity.ok(response);
	    }

	    // Health Check
	    @GetMapping("/check")
	    public String check() {
	        return "Auth Working";
	    }
}
