package com.ydg.restaurant.controller;

import com.ydg.restaurant.model.RestaurantTable;
import com.ydg.restaurant.service.TableService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {
    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping
    public RestaurantTable createTable(@RequestBody RestaurantTable table) {
        return tableService.addTable(table);
    }

    @GetMapping
    public List<RestaurantTable> getAll() {
        return tableService.getAllTables();
    }

    @PatchMapping("/{id}/status")
    public RestaurantTable updateStatus(@PathVariable Long id, @RequestParam boolean available) {
        return tableService.updateTableStatus(id, available);
    }
}