package com.example.tc.controller;

import com.example.tc.dto.ItemDTO;
import com.example.tc.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@Tag(name = "Items")
public class ItemController {
    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get item details",
               description = "Optional query param 'discount' (0-100) applies percentage discount to price")
    public ResponseEntity<?> getItem(@PathVariable String id,
                                     @RequestParam(required = false) Integer discount) {
        return service.getItemById(id, discount)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}