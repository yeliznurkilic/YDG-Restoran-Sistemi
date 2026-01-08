package com.ydg.restaurant.service;

import com.ydg.restaurant.model.MenuItem;
import com.ydg.restaurant.repository.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemService menuItemService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addMenuItem_ShouldThrow_WhenPriceInvalid() {
        MenuItem item = new MenuItem();
        item.setPrice(0);

        assertThrows(IllegalArgumentException.class, () -> menuItemService.addMenuItem(item));
    }

    @Test
    void updatePrice_ShouldUpdateValue() {
        MenuItem item = new MenuItem();
        item.setPrice(10);

        when(menuItemRepository.findById(1L)).thenReturn(java.util.Optional.of(item));
        when(menuItemRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        MenuItem updated = menuItemService.updatePrice(1L, 20);

        assertEquals(20, updated.getPrice());
    }
}
