package org.business.service;

import org.business.model.*;
import org.business.pojo.ReservationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {
    List<ReservationDto> getAllReservations(Pageable pageable);

    ReservationDto createReservation(Integer customerId,
                                     Integer restaurantId,
                                     ReservationDto newReservation);
}