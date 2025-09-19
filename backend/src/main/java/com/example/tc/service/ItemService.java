package com.example.tc.service;

import com.example.tc.dto.ItemDTO;
import com.example.tc.model.Item;
import com.example.tc.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository repo;

    public ItemService(ItemRepository repo) {
        this.repo = repo;
    }

    /**
     * Obtiene un Item y lo transforma a DTO, aplicando descuento si corresponde.
     *
     * @param id       identificador del item
     * @param discount descuento en porcentaje (0-100), opcional
     * @return Optional<ItemDTO>
     */
    public Optional<ItemDTO> getItemById(String id, Integer discount) {
        return repo.findById(id).map(item -> mapToDTO(item, discount));
    }

    /**
     * Convierte un Item (modelo completo) a un ItemDTO (respuesta simplificada para el frontend).
     *
     * @param item     modelo completo
     * @param discount descuento en porcentaje (0-100), opcional
     * @return ItemDTO
     */
    private ItemDTO mapToDTO(Item item, Integer discount) {
        double discountedPrice = item.getPrice();
        if (discount != null) {
            if (discount < 0 || discount > 100) {
                throw new IllegalArgumentException("Discount must be between 0 and 100");
            }
            discountedPrice = item.getPrice() * (1 - discount / 100.0);
        }

        return new ItemDTO(
                item.getId(),
                item.getTitle(),
                item.getSubtitle(),
                item.getDescription(),
                item.getPrice(),
                Math.round(discountedPrice * 100.0) / 100.0,
                item.getCurrency(),
                item.getAvailable_quantity(),
                item.getCondition(),
                item.getSeller() != null ? item.getSeller().getName() : null,
                item.getSeller() != null && item.getSeller().getRating() != null
                        ? item.getSeller().getRating().getScore()
                        : 0.0,
                item.getImages(),
                item.getReviews() != null ? item.getReviews().getAverage() : 0.0,
                item.getReviews() != null ? item.getReviews().getTotal() : 0,
                item.getHighlights()
        );
    }
}