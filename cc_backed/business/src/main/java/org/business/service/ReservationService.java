package org.business.service;

import org.business.pojo.ReservationDto;
import org.business.utils.PageableResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;

import java.util.List;

public interface ReservationService {
    PageableResponse<ReservationDto> findReservations(Pageable pageable, String searchString);

    ReservationDto createNewReservation(Integer customerId,
                                        Integer restaurantId,
                                        ReservationDto newReservation);

    boolean manageReservation(HttpMethod currentMethod,
                              Integer reservationId,
                              ReservationDto reservationDto);
}