package com.springboot.journalApp.controller;

import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(User user) {
        User newUser=userService.saveNewUser(user);
        if(newUser!=null){return new ResponseEntity<>("User Created...",HttpStatus.CREATED);}
        return new ResponseEntity<>("Id already exist...",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public void logout(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }
}
