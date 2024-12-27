package org.business.controller;

import org.business.pojo.ReservationDto;
import org.business.service.ReservationService;
import org.business.service.ReservationServiceImpl;
import org.business.service.RestaurantService;
import org.business.service.RestaurantServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("")
    public List<ReservationDto> getAllReservations(Pageable pageable) {
        return reservationService.findReservations(pageable);
    }

    @PostMapping("/{restaurantId}/{customerId}")
    public ResponseEntity<ReservationDto> addNewReservation(@PathVariable Integer restaurantId,
                                                            @PathVariable(required = false) Integer customerId,
                                                            @RequestBody ReservationDto newReservation) {
        ReservationDto reservation = reservationService.createNewReservation(customerId, restaurantId, newReservation);
        return ResponseEntity.ok()
                .body(reservation);
    }

    @PutMapping(value = "/{reservationId}")
    public ResponseEntity<Boolean> updateReservation(@RequestBody ReservationDto reservationDto,
                                                     @PathVariable Integer reservationId) {
        boolean response = reservationService.manageReservation(HttpMethod.PUT,
                reservationId,
                reservationDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Boolean> deleteReservation(@PathVariable Integer reservationId) {
        boolean response = reservationService.manageReservation(HttpMethod.DELETE,
                reservationId,
                null);
        return ResponseEntity.ok(response);
    }
}
