package com.karol.tastManagement.repository;


import com.karol.tastManagement.model.RefreshToken;
import com.karol.tastManagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepo extends MongoRepository<RefreshToken, String> {
    RefreshToken findByToken(String token);
    RefreshToken findByUser(User user);
}
