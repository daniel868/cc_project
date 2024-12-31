package org.business.controller;

import org.business.pojo.ReservationDto;
import org.business.service.ReservationService;
import org.business.utils.PageableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("")
    public PageableResponse<ReservationDto> getAllReservations(Pageable pageable,
                                                               @RequestParam(value = "searchString", required = false) String searchString,
                                                               @RequestParam(value = "searchDate", required = false) Long searchDate
    ) {
        return reservationService.findReservations(pageable, searchString, searchDate);
    }

    @PostMapping(value = "/{restaurantId}")
    public ResponseEntity<ReservationDto> addNewReservation(@PathVariable("restaurantId") Integer restaurantId,
                                                            @RequestBody ReservationDto newReservation) {
        ReservationDto reservation = reservationService.createNewReservation(null, restaurantId, newReservation);
        return ResponseEntity.ok()
                .body(reservation);
    }

    @PutMapping(value = "/{reservationId}")
    public ResponseEntity<Boolean> updateReservation(@RequestBody ReservationDto reservationDto,
                                                     @PathVariable Integer reservationId) {
        boolean response = reservationService.manageReservation(
                HttpMethod.PUT,
                reservationId,
                reservationDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Boolean> deleteReservation(@PathVariable Integer reservationId) {
        boolean response = reservationService.manageReservation(
                HttpMethod.DELETE,
                reservationId,
                null);
        return ResponseEntity.ok(response);
    }
}
