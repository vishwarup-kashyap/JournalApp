package com.springboot.journalApp.controller;


import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.UserRepo;
import com.springboot.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;
//    @GetMapping("/all-users")
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }

//    @PostMapping("/create-user")
//    public User createUser(User user) {
//        return userService.saveNewUser(user);
//    }

//    @GetMapping("/user-by-id/{id}")
//    public User getUserById(@PathVariable Long id) {
//        return userService.getUserById(id);
//    }
//
//    @DeleteMapping("/delete-user-by-id/{id}")
//    public void deleteUserById(@PathVariable Long id) {
//        userService.deleteUserById(id);
//    }

    @PutMapping("/update-user-by-username")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();

        User userInDb= userService.findByUsername(username);
        if(userInDb != null) {
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/delete-user")
    public ResponseEntity<?> DeleteUser(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User userInDb= userService.findByUsername(username);
        if(userInDb != null) {
            userRepo.deleteByUsername(username);
            return new ResponseEntity<>(HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
