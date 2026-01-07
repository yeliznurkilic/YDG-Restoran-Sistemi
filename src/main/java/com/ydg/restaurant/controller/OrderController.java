package com.ydg.restaurant.controller;

import com.ydg.restaurant.model.RestaurantOrder;
import com.ydg.restaurant.service.OrderService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public RestaurantOrder placeOrder(@RequestBody RestaurantOrder order) {
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<RestaurantOrder> getAll() {
        return orderService.getAllOrders();
    }

    @PatchMapping("/{id}/pay")
    public void pay(@PathVariable Long id) {
        orderService.markAsPaid(id);
    }
}