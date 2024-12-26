package org.business.service;

import org.business.pojo.ReservationDto;
import org.springframework.data.domain.Pageable;
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
    public List<ReservationDto> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(reservation -> new ReservationDto())
                .stream().toList();
    }

    @Override
    public Reservation createReservation(Integer customerId,
                                         ReservationDto reservationDto) {
        Reservation newReservation = new Reservation();
        return reservationRepository.save(newReservation);
    }

}