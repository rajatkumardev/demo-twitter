package com.example.demoTwitter.controller;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoTwitter.dto.UserAuthDto;
import com.example.demoTwitter.dto.UserDetailsDto;
import com.example.demoTwitter.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserAuthDto userAuthDto) {
        // todo validations

        try {
            boolean isLoginSuccessful = userService.login(userAuthDto);
            if (isLoginSuccessful) {
                return ResponseEntity.ok("Login Successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username/Password Incorrect");
            }

        } catch (UserPrincipalNotFoundException userNotFoundEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserAuthDto userAuthDto) {
        boolean userExists = userService.existsByEmail(userAuthDto.getEmail());
        if (userExists) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden, Account already exists");
        } else {
            userService.register(userAuthDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Account Creation Successful");
        }

    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@RequestParam("userID") Long userId) {
        UserDetailsDto userDetails = userService.getUserDetails(userId);
        if (userDetails != null) {
            return ResponseEntity.ok(userDetails);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDetailsDto>> getAllUsers() {
        List<UserDetailsDto> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/test")
    public String home() {
        return "Welcome to Twitter";
    }
}
