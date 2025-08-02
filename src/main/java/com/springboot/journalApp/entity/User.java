package com.springboot.journalApp.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
public class User {

    @Id
    private Long id;
    @Indexed(unique = true) //this will not to indexing automatically, you need to create index manually in application.properties
    @NonNull
    private String username;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntry> journalEntries=new ArrayList<>();
    private List<String> roles;
}
