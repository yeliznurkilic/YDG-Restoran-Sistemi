package com.ydg.restaurant.service;

import com.ydg.restaurant.model.MenuItem;
import com.ydg.restaurant.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem addMenuItem(MenuItem item) {
        if (item.getPrice() <= 0) {
            throw new IllegalArgumentException("Fiyat sıfır veya negatif olamaz!");
        }
        return menuItemRepository.save(item);
    }

    public List<MenuItem> getAllItems() {
        return menuItemRepository.findAll();
    }

    public MenuItem updatePrice(Long id, double newPrice) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));
        if (newPrice <= 0) throw new IllegalArgumentException("Yeni fiyat pozitif olmalı");
        item.setPrice(newPrice);
        return menuItemRepository.save(item);
    }

    public void deleteItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}