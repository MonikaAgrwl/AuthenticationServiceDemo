package com.demo.loginSignup.controller;

import com.demo.loginSignup.dto.LoginRequest;
import com.demo.loginSignup.dto.SignupRequest;
import com.demo.loginSignup.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        return userService.registerUser(signupRequest);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@Valid @PathVariable("id") String Id) throws Exception{
        return userService.getUser(Id);
    }
}
