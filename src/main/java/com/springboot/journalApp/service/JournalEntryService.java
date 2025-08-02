package com.springboot.journalApp.service;

import com.springboot.journalApp.entity.JournalEntry;
import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.JournalEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    public JournalEntryRepo journalEntryRepo;

    @Autowired
    public UserService userService;

    @Transactional
    public void createEntry(JournalEntry journalEntry, String username) {
        User user= userService.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + username);
        }
        journalEntry.setDate(new java.util.Date()); // Set the current date
        JournalEntry entry=journalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(entry);
        userService.saveUser(user);
    }
    public void createEntry(JournalEntry journalEntry) {
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries() {
         return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> getEntryById(Long id) {
        return journalEntryRepo.findById(id);
    }

    @Transactional
    public void deleteEntriesById(Long id,String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + username);
        }
        List<JournalEntry> entries = user.getJournalEntries();
        entries.removeIf(entry -> entry.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepo.deleteById(id);
    }

    public JournalEntry updateEntriesById(Long id, JournalEntry entry,String username) {
        User user= userService.findByUsername(username);
        if(user == null) {
            throw new IllegalArgumentException("User not found: " + username);
        }
        Optional<JournalEntry> existingEntry = journalEntryRepo.findById(id);
        existingEntry.ifPresent(journalEntry -> {
            journalEntry.setTitle(entry.getTitle());
            journalEntry.setContent(entry.getContent());// Update the date to current
            journalEntryRepo.save(journalEntry);
        });
        List<JournalEntry> entries = user.getJournalEntries();
        entries.removeIf(e -> e.getId().equals(id));
        userService.saveUser(user);
        entries.add(entry);
        userService.saveUser(user);
        return entry;
    }

}
