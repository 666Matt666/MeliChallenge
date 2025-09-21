package com.example.tc.service;

import com.example.tc.model.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Implementación de la estrategia de descuento que aplica un porcentaje fijo.
 */
@Service
public class FixedPercentageDiscount implements DiscountStrategy {

    // Se puede inyectar el valor del descuento desde la configuración
    @Value("${discount.percentage:10}") // Por defecto 10%
    private double discountPercentage;

    @Override
    public double applyDiscount(Item item) {
        if (item.getPrice() > 0) {
            return item.getPrice() * (1 - (discountPercentage / 100));
        }
        return item.getPrice();
    }
}