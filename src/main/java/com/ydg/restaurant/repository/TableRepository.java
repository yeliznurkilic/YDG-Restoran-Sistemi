package com.ydg.restaurant.repository;

import com.ydg.restaurant.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<RestaurantTable,Long> {
}
