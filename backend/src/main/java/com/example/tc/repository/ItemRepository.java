package com.example.tc.repository;

import com.example.tc.model.Item;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.core.io.ClassPathResource;
import com.fasterxml.jackson.databind.DeserializationFeature;
import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public class ItemRepository {
    private List<Item> items = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final ClassPathResource resource = new ClassPathResource("data/items.json");

    @PostConstruct
    public void init() {
        try {
            if (resource.exists()) {
                InputStream inputStream = resource.getInputStream();
                items = mapper.readValue(inputStream, new TypeReference<List<Item>>() {});
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize items repository", e);
        }
    }

    public List<Item> findAll() {
        return new ArrayList<>(items);
    }

    public Optional<Item> findById(String id) {
        return items.stream().filter(i -> i.getId().equals(id)).findFirst();
    }
    
    public Item save(Item item) {
        if (item.getId() == null) {
            item.setId(UUID.randomUUID().toString());
        }
        // Upsert logic
        items.removeIf(i -> i.getId().equals(item.getId()));
        items.add(item);
        persist();
        return item;
    }

    public void deleteById(String id) {
        items.removeIf(i -> i.getId().equals(id));
        persist();
    }

    private void persist() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(resource.getFile(), items);
        } catch (Exception e) {
            System.err.println("Failed to persist items.json. Changes will be lost on restart. Error: " + e.getMessage());
        }
    }
}
