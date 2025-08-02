package com.springboot.journalApp.repository;

import com.springboot.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JournalEntryRepo extends MongoRepository<JournalEntry, Long> {
    // Additional query methods can be defined here if needed
}
