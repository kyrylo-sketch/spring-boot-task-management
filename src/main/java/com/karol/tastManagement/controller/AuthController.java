package com.karol.tastManagement.controller;

import com.karol.tastManagement.model.User;
import com.karol.tastManagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthService.Result> register(@RequestBody User user){
        return authService.register(user);
    }

    @PostMapping("/login")
    public AuthService.Result login(@RequestBody User user){
        return authService.login(user);
    }
}
