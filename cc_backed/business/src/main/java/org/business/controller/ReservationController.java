package org.business.controller;

import org.business.model.*;
import org.business.service.ReservationServiceImpl;
import org.business.service.RestaurantServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {
    final ReservationServiceImpl reservationService;

    public ReservationController(ReservationServiceImpl reservationService, RestaurantServiceImpl restaurantService) {
        this.reservationService = reservationService;
        this.restaurantService = restaurantService;
    }

    final RestaurantServiceImpl restaurantService;

    @GetMapping("/reservations")
    public List<Restaurant> getAllReservations() {
        System.out.println("Getting all reservations ");
        List restaurantList = reservationService.getAllReservations();
        System.out.println("Received all reservations " + restaurantList.size());
        return restaurantList;
    }

    @PostMapping(path = "/reservations/{name}/add")
    public ResponseEntity<Reservation> addNewReservation(@PathVariable String name, @RequestBody Reservation newReservation) {
        int availableSpots = restaurantService.getAvailableSpots(name);
        Reservation reservation = new Reservation();
        if (availableSpots >= newReservation.getGuestNumber()) {
            System.out.println("Date is " + newReservation.getReservationDate());
            reservation = reservationService.makeReservation(newReservation);
            restaurantService.updateAvailableSpots(name, availableSpots - newReservation.getGuestNumber());
        }
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PostMapping(path = "/reservations/{name}/delete")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable String name, @RequestBody Reservation newReservation) {
        int availableSpots = restaurantService.getAvailableSpots(name);
        Reservation reservation = new Reservation();
        if (availableSpots >= newReservation.getGuestNumber()) {
            System.out.println("Date is " + newReservation.getReservationDate());
            reservation = reservationService.makeReservation(newReservation);
            restaurantService.updateAvailableSpots(name, availableSpots - newReservation.getGuestNumber());
        }
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }
}
