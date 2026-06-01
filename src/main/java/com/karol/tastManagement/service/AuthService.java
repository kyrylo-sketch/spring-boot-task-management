package com.karol.tastManagement.service;

import com.karol.tastManagement.model.RefreshToken;
import com.karol.tastManagement.model.User;
import com.karol.tastManagement.repository.CommentRepository;
import com.karol.tastManagement.repository.UserRepository;
import com.karol.tastManagement.security.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    public record Result(String accessToken, String refreshToken, User customer) {}

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

   // public User register(User user) {
      //  return userRepository.save(user);
   // }


    public ResponseEntity<Result> register(User customer){
        log.info("Register account request: customerId={}", customer.getId());
        User find = userRepository.findByEmail(customer.getEmail()).orElse(null);
        if(find != null){
            log.warn("Register account failed: email={} already exists", customer.getEmail());
            return new ResponseEntity<>(new Result("fail", "fail", null), HttpStatus.BAD_REQUEST);
        }else {
            customer.setPassword(encoder.encode(customer.getPassword()));
            userRepository.save(customer);
            log.info("Register account success: customerId={}", customer.getId());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(customer);
            return new ResponseEntity<>(new Result(jwtService.generateToken(customer.getEmail()), refreshToken.getToken(), customer), HttpStatus.OK) ;
        }

    }

    public Result login(User customer){
        log.info("Verify account request: customerId={}", customer.getId());
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPassword()));

        if(authentication.isAuthenticated()){
            User fullCustomer = userRepository.findByEmail(customer.getEmail()).orElse(null);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(fullCustomer);
            log.info("Authentication successful: email={}", customer.getEmail());
            return new Result(jwtService.generateToken(customer.getEmail()),refreshToken.getToken(), fullCustomer);

        }
        log.warn("Authentication failed: email={}", customer.getEmail());
        return new Result("fail", "fail",null);
    }
}
