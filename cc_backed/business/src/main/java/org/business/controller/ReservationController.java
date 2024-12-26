package org.business.controller;

import org.business.model.*;
import org.business.pojo.ReservationDto;
import org.business.utils.AppUtils;
import org.business.utils.GenericResponse;
import org.business.service.ReservationService;
import org.business.service.ReservationServiceImpl;
import org.business.service.RestaurantService;
import org.business.service.RestaurantServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    @PostMapping(path = "/reservations/{restaurantId}/{customerId}")
    public ResponseEntity<ReservationDto> addNewReservation(@PathVariable Integer restaurantId,
                                                            @PathVariable(required = false) Integer customerId,
                                                            @RequestBody ReservationDto newReservation) {
        ReservationDto reservation = reservationService.createReservation(customerId, restaurantId, newReservation);
        return ResponseEntity.ok()
                .body(reservation);
    }

    @DeleteMapping(path = "/reservations/{reservationId}/delete")
    public ResponseEntity<Boolean> deleteReservation(@PathVariable Integer reservationId) {

    }
}
