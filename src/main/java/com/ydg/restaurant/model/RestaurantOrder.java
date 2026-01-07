package com.ydg.restaurant.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "restaurant_orders")
public class RestaurantOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RestaurantTable table;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MenuItem> items;

    private double totalPrice;
    private boolean isPaid = false;
}