package org.business.service;

import org.springframework.stereotype.Service;

import org.business.model.Reservation;
import org.business.model.Restaurant;
import org.business.repository.ReservationRepository;
import org.business.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
    }

    final RestaurantRepository restaurantRepository;

    @Override
    public List<Reservation> getAllReservations() {
        Iterator<Reservation> reservationIt = reservationRepository.findAll().iterator();
        List<Reservation> reservationList = new ArrayList<>();

        for (Iterator<Reservation> it = reservationIt; it.hasNext(); ) {
            Reservation reservation = it.next();
            reservationList.add(reservation);
        }

        for (Reservation reservation : reservationList) {
            System.out.println("Got reservation: " + reservation.getId());
        }

        return reservationList;
    }

    @Override
    public List<Reservation> findReservationByRestaurantName(String name) {
        List<Restaurant> restaurantList = restaurantRepository.findByName(name);
        int restaurantId = 0;
        for (Restaurant restaurant : restaurantList) {
            if (restaurant.getName().equals(name)) {
                restaurantId = restaurant.getId();
            }
        }
        return reservationRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public Reservation makeReservation(Reservation newReservation) {
        return reservationRepository.save(newReservation);
    }

}