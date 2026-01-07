package com.ydg.restaurant.service;

import com.ydg.restaurant.model.RestaurantTable;
import com.ydg.restaurant.repository.TableRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TableService {
    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public RestaurantTable addTable(RestaurantTable table) {
        return tableRepository.save(table);
    }

    public List<RestaurantTable> getAllTables() {
        return tableRepository.findAll();
    }

    public RestaurantTable updateTableStatus(Long id, boolean status) {
        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Masa bulunamadı"));
        table.setAvailable(status);
        return tableRepository.save(table);
    }
}