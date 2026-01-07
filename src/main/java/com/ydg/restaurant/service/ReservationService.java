package com.ydg.restaurant.service;

import com.ydg.restaurant.model.Reservation;
import com.ydg.restaurant.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation makeReservation(Reservation res) {
        //Masa Kapasitesi Uygun mu?
        if (res.getNumberOfPeople() > res.getRestaurantTable().getCapacity()) {
            throw new IllegalArgumentException("HATA: Masa kapasitesi ("
                    + res.getRestaurantTable().getCapacity() + " kişi) gelen grup için yetersiz!");
        }

        //Masa O Saatte Dolu mu?
        List<Reservation> existingReservations = reservationRepository.findAll();
        for (Reservation existing : existingReservations) {
            // Aynı masa mı?
            if (existing.getRestaurantTable().getId().equals(res.getRestaurantTable().getId())) {
                // Aynı tarih ve saat mi?
                if (existing.getReservationTime().equals(res.getReservationTime())) {
                    throw new IllegalStateException("HATA: Bu masa seçilen saatte zaten rezerve edilmiş!");
                }
            }
        }
        return reservationRepository.save(res);
    }
    public List<Reservation> getAll() { return reservationRepository.findAll(); }

    public void cancelReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}