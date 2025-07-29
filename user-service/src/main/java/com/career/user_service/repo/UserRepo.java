package com.career.user_service.repo;

import com.career.user_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, String> {
    // Custom query methods can be defined here if needed
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
}
