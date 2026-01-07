package com.ydg.restaurant.repository;

import com.ydg.restaurant.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {}