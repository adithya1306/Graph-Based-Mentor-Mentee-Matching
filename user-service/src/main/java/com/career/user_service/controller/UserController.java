package com.career.user_service.controller;

import com.career.user_service.model.LoginReq;
import com.career.user_service.model.User;
import com.career.user_service.model.VerifyReq;
import com.career.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("")
    public String getUsers() {
        return "List of users";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User userRequest) {
        return new ResponseEntity<>(service.createUser(userRequest), org.springframework.http.HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginReq userRequest) {
        return new ResponseEntity<>(service.loginUser(userRequest), org.springframework.http.HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyReq verifyRequest) {
        return service.verifyUser(verifyRequest);
    }
}
