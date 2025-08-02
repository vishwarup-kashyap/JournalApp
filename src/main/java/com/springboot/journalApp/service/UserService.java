package com.springboot.journalApp.service;


import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

//    Logger logger = LoggerFactory.getLogger(UserService.class);

    public User saveUser(User user) {
       return userRepo.save(user);
    }

    public User saveNewUser(User user){
        User dbUser= userRepo.findById(user.getId()).orElse(null);
        if(dbUser!=null) {

            log.info("save user {}",user.getUsername());
            log.debug("save user {}",user.getUsername());
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));

//        logger.info("save user "+user);
        return userRepo.save(user);
    }
    public User saveAdminUser(User user){
        User dbUser= userRepo.findById(user.getId()).orElse(null);
        if(dbUser!=null) {

            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public Optional<?> deleteUserById(Long id) {
        return userRepo.findById(id).map(user -> {
            userRepo.deleteById(id);
            return user;
        });
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }


}
