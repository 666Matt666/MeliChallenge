package com.example.tc.repository;

import com.example.tc.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = new File("src/main/resources/data/users.json");
    private List<User> users = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                mapper.writeValue(file, users);
            } else {
                users = mapper.readValue(file, new TypeReference<List<User>>() {});
            }
        } catch (Exception e) {
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
        users.removeIf(user -> user.getId().equals(u.getId()));
        users.add(u);
        persist();
        return u;
    }

    private void persist(){
        try { mapper.writeValue(file, users); } catch(Exception e){ throw new RuntimeException(e);}
    }
}
