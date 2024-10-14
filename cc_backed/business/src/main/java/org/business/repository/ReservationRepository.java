package org.business.repository;


import org.business.model.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    List<Reservation> findByRestaurantId(int id);

}
