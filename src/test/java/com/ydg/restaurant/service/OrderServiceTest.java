package com.ydg.restaurant.service;

import com.ydg.restaurant.model.MenuItem;
import com.ydg.restaurant.model.RestaurantOrder;
import com.ydg.restaurant.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_ShouldCalculateTotalPrice() {
        MenuItem m1 = new MenuItem();
        m1.setPrice(10.0);

        MenuItem m2 = new MenuItem();
        m2.setPrice(15.0);

        RestaurantOrder order = new RestaurantOrder();
        order.setItems(List.of(m1, m2));

        when(orderRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        RestaurantOrder saved = orderService.createOrder(order);

        assertEquals(25.0, saved.getTotalPrice());
    }

    @Test
    void markAsPaid_ShouldSetPaidTrue() {
        RestaurantOrder order = new RestaurantOrder();
        order.setPaid(false);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.markAsPaid(1L);

        assertTrue(order.isPaid());
        verify(orderRepository).save(order);
    }
}
