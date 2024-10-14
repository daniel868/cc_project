package org.business.service;

import org.business.model.*;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservations();

    List<Reservation> findReservationByRestaurantName(String name);

    Reservation makeReservation(Reservation newReservation);
}