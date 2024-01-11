package com.example.business_logic.service;

import com.example.business_logic.model.Reservation;
import com.example.business_logic.model.Restaurant;
import com.example.business_logic.repository.ReservationRepository;
import com.example.business_logic.repository.RestaurantRepository;
import com.example.business_logic.service.ReservationService;
import org.springframework.stereotype.Service;

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
        Iterator<Reservation> reservationIt= reservationRepository.findAll().iterator();
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
        for(Restaurant restaurant : restaurantList) {
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
