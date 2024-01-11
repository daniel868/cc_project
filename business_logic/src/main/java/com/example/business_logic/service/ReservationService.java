package com.example.business_logic.service;

import com.example.business_logic.model.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservations();

    List<Reservation> findReservationByRestaurantName(String name);
    Reservation makeReservation(Reservation newReservation);
}
