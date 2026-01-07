package com.ydg.restaurant.repository;

import com.ydg.restaurant.model.RestaurantOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<RestaurantOrder, Long> {
}
