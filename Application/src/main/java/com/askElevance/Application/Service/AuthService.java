package com.askElevance.Application.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.askElevance.Application.Dto.AuthResponse;
import com.askElevance.Application.Dto.LoginRequest;
import com.askElevance.Application.Dto.RegisterRequest;
import com.askElevance.Application.Entity.User;
import com.askElevance.Application.Repo.UserRepo;



@Service
public class AuthService {
	 @Autowired
	    private UserRepo userRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @Autowired
	    private AuthenticationManager authenticationManager;

	    // REGISTER USER
	    public void register(RegisterRequest request) {

	        User user = new User();

	        user.setName(request.getName());

	        user.setPassword(
	                passwordEncoder.encode(request.getPassword())
	        );

	       
	        userRepository.save(user);
	    }

	    // LOGIN USER
	    public AuthResponse login(LoginRequest request) {

	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        request.getEmail(),
	                        request.getPassword()
	                )
	        );

	        // Later you can generate JWT token here

	        return new AuthResponse(
	                "Login Successful"
	        );
	        
	    }
	

}

