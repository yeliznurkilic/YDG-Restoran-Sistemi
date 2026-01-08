package com.ydg.restaurant.service;

import com.ydg.restaurant.model.Reservation;
import com.ydg.restaurant.model.RestaurantTable;
import com.ydg.restaurant.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void makeReservation_ShouldThrow_WhenCapacityExceeded() {
        RestaurantTable table = new RestaurantTable();
        table.setCapacity(4);

        Reservation res = new Reservation();
        res.setRestaurantTable(table);
        res.setNumberOfPeople(6);

        assertThrows(IllegalArgumentException.class, () -> reservationService.makeReservation(res));
    }

    @Test
    void makeReservation_ShouldThrow_WhenTimeConflictExists() {
        RestaurantTable table = new RestaurantTable();
        table.setId(1L);
        table.setCapacity(4); // capacity set eklendi!

        Reservation existing = new Reservation();
        existing.setRestaurantTable(table);
        existing.setReservationTime(LocalDateTime.of(2024, 1, 1, 18, 0));

        when(reservationRepository.findAll()).thenReturn(List.of(existing));

        Reservation newRes = new Reservation();
        newRes.setRestaurantTable(table);
        newRes.setReservationTime(LocalDateTime.of(2024, 1, 1, 18, 0));
        newRes.setNumberOfPeople(2);

        assertThrows(IllegalStateException.class, () -> reservationService.makeReservation(newRes));
    }


    @Test
    void makeReservation_ShouldSave_WhenDataIsValid() {
        RestaurantTable table = new RestaurantTable();
        table.setId(1L);
        table.setCapacity(4);

        Reservation res = new Reservation();
        res.setRestaurantTable(table);
        res.setReservationTime(LocalDateTime.now());
        res.setNumberOfPeople(2);

        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());
        when(reservationRepository.save(any())).thenReturn(res);

        Reservation saved = reservationService.makeReservation(res);

        assertNotNull(saved);
        verify(reservationRepository, times(1)).save(res);
    }
}
