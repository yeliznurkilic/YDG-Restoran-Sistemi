package com.ydg.restaurant.service;

import com.ydg.restaurant.model.RestaurantOrder;
import com.ydg.restaurant.model.MenuItem;
import com.ydg.restaurant.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public RestaurantOrder createOrder(RestaurantOrder order) {
        // Toplam tutarı listedeki ürün fiyatlarından topla
        double total = order.getItems().stream()
                .mapToDouble(MenuItem::getPrice)
                .sum();
        order.setTotalPrice(total);
        return orderRepository.save(order);
    }

    public List<RestaurantOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public void markAsPaid(Long id) {
        RestaurantOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı"));
        order.setPaid(true);
        orderRepository.save(order);
    }
}