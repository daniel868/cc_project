package com.example.business_logic.repository;

import com.example.business_logic.model.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    List<Reservation> findByRestaurantId(int id);
}
