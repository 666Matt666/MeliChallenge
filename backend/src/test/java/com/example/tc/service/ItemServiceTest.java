package com.example.tc.service;

import com.example.tc.dto.ItemDTO;
import com.example.tc.model.Item;
import com.example.tc.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    private ItemRepository repo;
    private ItemService service;

    @BeforeEach
    void setUp() {
        repo = mock(ItemRepository.class);
        service = new ItemService(repo);
    }

    private Item buildMockItem() {
        Item item = new Item();
        item.setId("123");
        item.setTitle("Laptop Gamer");
        item.setSubtitle("16GB RAM, RTX 3060");
        item.setDescription("High performance gaming laptop");
        item.setPrice(1000.0);
        item.setCurrency("USD");
        item.setAvailable_quantity(5);
        item.setCondition("new");

        Item.Seller seller = new Item.Seller();
        seller.setId("s1");
        seller.setName("TechStore");

        Item.Rating rating = new Item.Rating();
        rating.setLevel("gold");
        rating.setScore(4.8);
        rating.setTotal_ratings(120);

        seller.setRating(rating);
        item.setSeller(seller);

        item.setImages(List.of("http://example.com/img1.jpg"));

        return item;
    }

    @Test
    void getItemById_shouldReturnDTO_whenItemExists() {
        Item mockItem = buildMockItem();
        when(repo.findById("123")).thenReturn(Optional.of(mockItem));

        Optional<ItemDTO> result = service.getItemById("123", null);

        assertTrue(result.isPresent());
        assertEquals("Laptop Gamer", result.get().getTitle());
        assertEquals(1000.0, result.get().getPrice());
        assertEquals(1000.0, result.get().getDiscountedPrice());
        assertEquals("TechStore", result.get().getSellerName());
    }

    @Test
    void getItemById_shouldApplyDiscount_whenValidDiscountProvided() {
        Item mockItem = buildMockItem();
        when(repo.findById("123")).thenReturn(Optional.of(mockItem));

        Optional<ItemDTO> result = service.getItemById("123", 20);

        assertTrue(result.isPresent());
        assertEquals(800.0, result.get().getDiscountedPrice());
    }

    @Test
    void getItemById_shouldThrowException_whenDiscountInvalid() {
        Item mockItem = buildMockItem();
        when(repo.findById("123")).thenReturn(Optional.of(mockItem));

        assertThrows(IllegalArgumentException.class,
                () -> service.getItemById("123", 150));
    }

    @Test
    void getItemById_shouldReturnEmpty_whenItemNotFound() {
        when(repo.findById("999")).thenReturn(Optional.empty());

        Optional<ItemDTO> result = service.getItemById("999", null);

        assertTrue(result.isEmpty());
    }
}
