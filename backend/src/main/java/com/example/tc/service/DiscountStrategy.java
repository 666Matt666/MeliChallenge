package com.example.tc.service;

import com.example.tc.model.Item;

/**
 * Interfaz que define la estrategia para aplicar un descuento.
 * Esto permite agregar nuevas reglas de descuento sin modificar el código existente.
 */
public interface DiscountStrategy {
    double applyDiscount(Item item);
}