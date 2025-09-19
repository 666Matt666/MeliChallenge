package com.example.tc.repository;

import com.example.tc.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.core.io.ClassPathResource;
import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    // Use ClassPathResource for reading from the classpath in a reliable way
    private final ClassPathResource resource = new ClassPathResource("data/users.json");
    private List<User> users = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            // Read the file from the classpath
            if (resource.exists()) {
                InputStream inputStream = resource.getInputStream();
                // The TypeReference is needed to deserialize a list of objects
                users = mapper.readValue(inputStream, new TypeReference<List<User>>() {});
            }
        } catch (Exception e) {
            // This will cause the application to fail to start, which is what we see.
            // The original code used `new File()` which is not reliable for classpath resources.
            throw new RuntimeException("Failed to init users store", e);
        }
    }

    public List<User> findAll() { return new ArrayList<>(users); }

    public Optional<User> findById(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Optional<User> findByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equalsIgnoreCase(username)).findFirst();
    }

    public User save(User u) {
        if (u.getId() == null) {
            u.setId(UUID.randomUUID().toString());
        }
        // This is an upsert logic: remove if exists, then add.
        users.removeIf(user -> user.getId().equals(u.getId()));
        users.add(u);
        persist();
        return u;
    }

    private void persist(){
        try {
            // Writing back to the source file is not a good production practice,
            // as classpath resources can be inside a read-only JAR.
            // This is for demonstration purposes in a dev environment only.
            mapper.writerWithDefaultPrettyPrinter().writeValue(resource.getFile(), users);
        } catch(Exception e){
            // In a real app, log this error. For now, we'll print to stderr.
            System.err.println("Failed to persist users.json. Changes will be lost on restart. Error: " + e.getMessage());
        }
    }
}
