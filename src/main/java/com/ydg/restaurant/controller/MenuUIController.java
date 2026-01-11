package com.ydg.restaurant.controller;

import com.ydg.restaurant.model.MenuItem;
import com.ydg.restaurant.service.MenuItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ui/menu")
public class MenuUIController {

    private final MenuItemService menuItemService;

    public MenuUIController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public String viewMenu(Model model) {
        model.addAttribute("items", menuItemService.getAllItems());
        model.addAttribute("newItem", new MenuItem());
        return "menu";
    }

    @PostMapping("/add")
    public String add(MenuItem item) {
        menuItemService.addMenuItem(item);
        return "redirect:/ui/menu";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @RequestParam double price) {
        menuItemService.updatePrice(id, price);
        return "redirect:/ui/menu";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        menuItemService.deleteItem(id);
        return "redirect:/ui/menu";
    }
}
