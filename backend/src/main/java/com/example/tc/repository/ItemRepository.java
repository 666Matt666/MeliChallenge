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
import java.util.Optional;

@Repository
public class ItemRepository {
    private List<Item> items = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @PostConstruct
    public void init() {
        try {
            ClassPathResource resource = new ClassPathResource("data/items.json");
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
    
    public Item save(Item item) throws Exception {
        items.add(item);
        // mapper.writeValue(file, items); // Esta línea causaba el error de compilación
        return item;
    }
}
