package com.askElevance.Application.Entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // works with Oracle Auto-Increment/Identity
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String role;  // example: "EMPLOYEE", "ADMIN", "MANAGER"

    private LocalDateTime createdAt = LocalDateTime.now();
    
    private String password;
}

