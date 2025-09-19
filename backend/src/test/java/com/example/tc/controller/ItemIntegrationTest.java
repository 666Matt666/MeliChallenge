package com.example.tc.controller;

import com.example.tc.model.Item;
import com.example.tc.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Levanta el contexto completo de Spring Boot
@SpringBootTest
@AutoConfigureMockMvc
class ItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository repo;

    @BeforeEach
    void setupData() throws Exception {
        // Inicializar con un item de prueba
        Item item = new Item();
        item.setId("100");
        item.setTitle("Smartphone X");
        item.setSubtitle("128GB, Dual SIM");
        item.setDescription("Latest gen smartphone");
        item.setPrice(500.0);
        item.setCurrency("USD");
        item.setAvailable_quantity(10);
        item.setCondition("new");

        Item.Seller seller = new Item.Seller();
        seller.setId("s10");
        seller.setName("BestSeller");
        Item.Rating rating = new Item.Rating();
        rating.setLevel("platinum");
        rating.setScore(4.7);
        rating.setTotal_ratings(250);
        seller.setRating(rating);
        item.setSeller(seller);

        item.setImages(List.of("http://example.com/smartphone.jpg"));

        // Guardamos en el repo (simulado sobre JSON en memoria)
        repo.save(item);
    }

    @Test
    void getItemById_shouldReturnItem() throws Exception {
        mockMvc.perform(get("/api/items/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("100")))
                .andExpect(jsonPath("$.title", is("Smartphone X")))
                .andExpect(jsonPath("$.price", is(500.0)))
                .andExpect(jsonPath("$.discountedPrice", is(500.0)));
    }

    @Test
    void getItemById_withDiscount_shouldReturnDiscountedPrice() throws Exception {
        mockMvc.perform(get("/api/items/100")
                        .param("discount", "20") // aplicar 20% de descuento
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("100")))
                .andExpect(jsonPath("$.title", is("Smartphone X")))
                .andExpect(jsonPath("$.price", is(500.0)))
                .andExpect(jsonPath("$.discountedPrice", is(400.0)));
    }

    @Test
    void getItemById_whenNotFound_shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/items/999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
