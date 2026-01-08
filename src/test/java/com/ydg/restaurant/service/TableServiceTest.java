package com.ydg.restaurant.service;

import com.ydg.restaurant.model.RestaurantTable;
import com.ydg.restaurant.repository.TableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TableServiceTest {

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private TableService tableService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateTableStatus_ShouldChangeAvailability() {
        RestaurantTable table = new RestaurantTable();
        table.setAvailable(true);

        when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
        when(tableRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        RestaurantTable updated = tableService.updateTableStatus(1L, false);

        assertFalse(updated.isAvailable());
        verify(tableRepository).save(table);
    }
}
