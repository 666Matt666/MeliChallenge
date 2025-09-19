package com.example.tc.repository;

import com.example.tc.model.Item;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import jakarta.annotation.PostConstruct;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemRepository {
    private final File file = new File("src/main/resources/data/items.json");
    private List<Item> items = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @PostConstruct
    public void init() {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                mapper.writeValue(file, items);
            } else {
                items = mapper.readValue(file, new TypeReference<List<Item>>() {});
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
        mapper.writeValue(file, items);
        return item;
    }
}
