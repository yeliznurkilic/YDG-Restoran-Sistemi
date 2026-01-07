package com.ydg.restaurant.controller;

import com.ydg.restaurant.model.Category;
import com.ydg.restaurant.model.MenuItem;
import com.ydg.restaurant.service.CategoryService;
import com.ydg.restaurant.service.MenuItemService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final CategoryService categoryService;
    private final MenuItemService menuItemService;

    public MenuController(CategoryService categoryService, MenuItemService menuItemService) {
        this.categoryService = categoryService;
        this.menuItemService = menuItemService;
    }

    @PostMapping("/categories")
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/items")
    public MenuItem addItem(@RequestBody MenuItem item) {
        return menuItemService.addMenuItem(item);
    }

    @PutMapping("/items/{id}/price")
    public MenuItem updatePrice(@PathVariable Long id, @RequestParam double price) {
        return menuItemService.updatePrice(id, price);
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Long id) {
        menuItemService.deleteItem(id);
    }
}