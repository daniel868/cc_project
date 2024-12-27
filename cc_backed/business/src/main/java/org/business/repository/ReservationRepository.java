package org.business.repository;


import org.business.model.Reservation;
import org.service.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query(value = "select r from Reservation r where r.guestName like  %:searchString%")
    Page<Reservation> searchReservationByGuestNameLike(@Param("searchString") String searchString, Pageable pageable);
}
