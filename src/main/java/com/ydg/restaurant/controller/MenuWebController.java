package com.ydg.restaurant.controller;

import com.ydg.restaurant.model.Category;
import com.ydg.restaurant.model.MenuItem;
import com.ydg.restaurant.service.CategoryService;
import com.ydg.restaurant.service.MenuItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/menu")
public class MenuWebController {

    private final MenuItemService menuItemService;
    private final CategoryService categoryService;

    public MenuWebController(MenuItemService menuItemService, CategoryService categoryService) {
        this.menuItemService = menuItemService;
        this.categoryService = categoryService;
    }

    // ---- WEB UI Menü Görüntüleme ----
    @GetMapping
    public String showMenu(Model model) {
        model.addAttribute("items", menuItemService.getAllItems());
        return "menu"; // -> templates/menu.html
    }

    // ---- Ürün Ekleme Formu ----
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("item", new MenuItem());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "menu-add"; // -> templates/menu-add.html
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute MenuItem item, @RequestParam Long categoryId) {
        Category cat = categoryService.getById(categoryId);
        item.setCategory(cat);
        menuItemService.addMenuItem(item);
        return "redirect:/menu";
    }

    // ---- Fiyat Güncelleme ----
    @PostMapping("/update-price/{id}")
    public String updatePrice(@PathVariable Long id, @RequestParam double price) {
        menuItemService.updatePrice(id, price);
        return "redirect:/menu";
    }

    // ---- Ürün Silme ----
    @PostMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        menuItemService.deleteItem(id);
        return "redirect:/menu";
    }
}
