package org.business.service;

import org.business.pojo.ReservationDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;

import java.util.List;

public interface ReservationService {
    List<ReservationDto> findReservations(Pageable pageable);

    ReservationDto createNewReservation(Integer customerId,
                                        Integer restaurantId,
                                        ReservationDto newReservation);

    boolean manageReservation(HttpMethod currentMethod,
                              Integer reservationId,
                              ReservationDto reservationDto);
}