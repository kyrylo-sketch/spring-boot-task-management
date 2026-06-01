package com.karol.tastManagement.service;

import com.karol.tastManagement.model.User;
import com.karol.tastManagement.repository.CommentRepository;
import com.karol.tastManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    
    public User register(User user) {
        return userRepository.save(user);
    }

    public User login(User user) {
        User found = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (found.getPassword().equals(user.getPassword())) return found;
        else return null;
    }
}
