package com.ydg.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private RestaurantTable restaurantTable;

    private LocalDateTime reservationTime;
    private int numberOfPeople;
}