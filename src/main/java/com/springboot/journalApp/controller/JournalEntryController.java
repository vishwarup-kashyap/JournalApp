//package com.springboot.journalApp.controller;
//
//
//import com.springboot.journalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/_journal")
//public class JournalEntryController {
//
//    private Map<Long, JournalEntry> journalEntries = new HashMap<>();
//
//    @GetMapping("/entries")
//    public List<JournalEntry> getAllEntries() {
//        return new ArrayList<>(journalEntries.values());
//    }
//
//    @PostMapping("/add-entries")
//    public boolean createEntry(@RequestBody JournalEntry entry) {
//        journalEntries.put(entry.getId(), entry);
//        return true;
//    }
//
//    @GetMapping("/entries-by-id/{id}")
//    public JournalEntry getEntryById(@PathVariable long id) {
//        return journalEntries.get(id);
//    }
//
//    @DeleteMapping("/delete-entries-by-id/{id}")
//    public boolean deleteEntriesById(@PathVariable long id){
//        if(journalEntries.containsKey(id)){
//            journalEntries.remove(id);
//            return true;
//        }else{
//            return false;
//        }
//    }
//
//    @PutMapping("/update-entries-by-id/{id}")
//    public JournalEntry updateEntriesById(@PathVariable long id, @RequestBody JournalEntry entry) {
//        if(journalEntries.containsKey(id)){
//            journalEntries.put(id, entry);
//            return entry;
//        } else {
//            return null; // or throw an exception
//        }
//    }
//}
