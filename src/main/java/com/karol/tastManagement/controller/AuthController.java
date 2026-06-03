package com.karol.tastManagement.controller;

import com.karol.tastManagement.model.RefreshToken;
import com.karol.tastManagement.model.User;
import com.karol.tastManagement.security.JWTService;
import com.karol.tastManagement.service.AuthService;
import com.karol.tastManagement.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JWTService jwtService;

    @PostMapping("/register")
    public AuthService.Result register(@RequestBody User user){
        return authService.register(user);
    }

    @PostMapping("/login")
    public AuthService.Result login(@RequestBody User user){
        return authService.login(user);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String,String> body){
        String token = body.get("refreshToken");
        RefreshToken refreshToken = refreshTokenService.findByToken(token);
        if(refreshToken == null || !refreshTokenService.isValid(refreshToken)){
            return new ResponseEntity<>("Token invalid", HttpStatus.BAD_REQUEST);
        }
        else{
            String newAccessToken = jwtService.generateToken(refreshToken.getUser().getName());
            return new ResponseEntity<>(Map.of("accessToken", newAccessToken), HttpStatus.OK);
        }
    }
}
