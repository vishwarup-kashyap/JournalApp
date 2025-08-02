package com.springboot.journalApp.controller;


import com.springboot.journalApp.entity.JournalEntry;
import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.service.JournalEntryService;
import com.springboot.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
//controller create endpoints and call service for business logic

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/all-entries")
    public ResponseEntity<?> getAllEntriesOfUser() {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user= userService.findByUsername(username);
        if(user.getJournalEntries() == null || user.getJournalEntries().isEmpty()) {
            return new ResponseEntity<>("No journal entries found for user: " + username, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.getJournalEntries(), HttpStatus.OK);
    }


    @PostMapping("/add-entries")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry entry) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            journalEntryService.createEntry(entry, username);
            return new ResponseEntity<>("Entry created successfully", HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Error creating entry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/entries-by-id/{id}")
    public ResponseEntity<?> getEntryById(@PathVariable Long id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user=userService.findByUsername(username);
        List<JournalEntry> journalEntry=user.getJournalEntries().stream().filter(entry->entry.getId().equals(id)).collect(Collectors.toList());

        if(journalEntry.isEmpty()) {return new ResponseEntity<>("No journal entries found for id: " + id, HttpStatus.NOT_FOUND);}
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);

    }

    @DeleteMapping("/delete-entries-by-id/{id}")
    public ResponseEntity<?> deleteEntriesById(@PathVariable Long id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user=userService.findByUsername(username);
        List<JournalEntry> journalEntry=user.getJournalEntries().stream().filter(entry->entry.getId().equals(id)).collect(Collectors.toList());
        if(journalEntry.isEmpty()) {return new ResponseEntity<>("No journal entries found for id: " + id, HttpStatus.NOT_FOUND);}
        journalEntryService.deleteEntriesById(id,username);
        return new ResponseEntity<>("Entry deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/update-entries-by-id/{id}")
    public ResponseEntity<?> updateEntriesById(@PathVariable Long id, @RequestBody JournalEntry entry) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user=userService.findByUsername(username);
        List<JournalEntry> journalEntry = user.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
        if(journalEntry.isEmpty()) {return new ResponseEntity<>("Id not found...", HttpStatus.NOT_FOUND);}
        JournalEntry old=journalEntry.get(0);
        old.setTitle(entry.getTitle()!=null&& !entry.getTitle().isEmpty() ? entry.getTitle() : old.getTitle());
        old.setContent(entry.getContent()!=null && !entry.getContent().isEmpty() ? entry.getContent() : old.getContent());
        journalEntryService.createEntry(old);
        return new ResponseEntity<>(old, HttpStatus.OK);
    }



}
