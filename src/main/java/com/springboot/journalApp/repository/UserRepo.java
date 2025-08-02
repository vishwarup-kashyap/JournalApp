package com.springboot.journalApp.repository;

import com.springboot.journalApp.entity.User;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, Long> {
    // Additional query methods can be defined here if needed
    User findByUsername(String username);

    void deleteByUsername(String username);
}
