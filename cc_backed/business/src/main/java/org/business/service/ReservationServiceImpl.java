package org.business.service;

import org.business.exceptions.NotEnoughSpotsException;
import org.business.model.Customer;
import org.business.pojo.ReservationDto;
import org.business.repository.CustomerRepository;
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
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;


    public ReservationServiceImpl(ReservationRepository reservationRepository, CustomerRepository customerRepository, RestaurantRepository restaurantRepository) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
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
    public ReservationDto createReservation(Integer customerId,
                                            Integer restaurantId,
                                            ReservationDto reservationDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElse(null);
        if (restaurant == null) {
            throw new RuntimeException("Could not find restaurant with id: " + restaurantId);
        }

        boolean enoughSpotsForRestaurant = isEnoughSpotsForRestaurant(reservationDto, restaurant);
        if (!enoughSpotsForRestaurant) {
            throw new NotEnoughSpotsException("Not enough spots for booking");
        }

        Customer customer = null;
        if (customerId != null) {
            customer = customerRepository.findById(customerId)
                    .orElse(null);
        }

        Reservation reservation = new Reservation();
        if (customer != null) {
            reservation.setCustomer(customer);
        }
        reservation.setGuestCount(reservationDto.getGuestCount());
        reservation.setReservationDate(reservationDto.getReservationDate());
        reservation.setGuestName(customer != null ? customer.getName() : reservationDto.getReservationGuestName());
        reservation.setGuestCount(reservationDto.getGuestCount());

        int remainingAvailableSpots = restaurant.getAvailableSpots() - reservationDto.getGuestCount();
        restaurant.setAvailableSpots(remainingAvailableSpots);
        restaurant.addReservation(reservation);

        restaurantRepository.save(restaurant);

        return reservationDto;
    }


    private boolean isEnoughSpotsForRestaurant(ReservationDto newReservation,
                                               Restaurant currentRestaurant) {
        if (newReservation.getGuestCount() <= currentRestaurant.getAvailableSpots()) {
            return true;
        }
        return false;
    }
}