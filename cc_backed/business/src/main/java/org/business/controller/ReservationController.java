package org.business.controller;

import org.business.model.*;
import org.business.pojo.ReservationDto;
import org.business.pojo.generic.GenericResponse;
import org.business.service.ReservationService;
import org.business.service.ReservationServiceImpl;
import org.business.service.RestaurantService;
import org.business.service.RestaurantServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;
    private final RestaurantService restaurantService;

    public ReservationController(ReservationServiceImpl reservationService, RestaurantServiceImpl restaurantService) {
        this.reservationService = reservationService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/reservations")
    public List<ReservationDto> getAllReservations(Pageable pageable) {
        return reservationService.getAllReservations(pageable);
    }

    @PostMapping(path = "/reservations/{customerId}")
    public GenericResponse<?> addNewReservation(@PathVariable Integer customerId,
                                                @RequestBody ReservationDto newReservation) {
        Reservation reservation = new Reservation();
        if (newReservation >= newReservation.getGuestCount()) {
            logger.debug("Date is {}", newReservation.getReservationDate());
            reservation = reservationService.createReservation(newReservation);
            restaurantService.updateAvailableSpots(newReservation.getRestaurantName(),
                    availableSpots - newReservation.getGuestCount());
        }
        return new GenericResponse<>();
    }

    @DeleteMapping(path = "/reservations/{reservationId}/delete")
    public GenericResponse<?> deleteReservation(@PathVariable Integer reservationId) {
        int availableSpots = restaurantService.getAvailableSpots(name);
        Reservation reservation = new Reservation();
        if (availableSpots >= newReservation.getGuestCount()) {
            System.out.println("Date is " + newReservation.getReservationDate());
            reservation = reservationService.createReservation(newReservation);
            restaurantService.updateAvailableSpots(name, availableSpots - newReservation.getGuestCount());
        }
        return new GenericResponse<>();
    }
}
