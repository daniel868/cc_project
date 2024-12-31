package org.business.repository;

import org.business.model.Reservation;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ReservationSpecification {
    public static Specification<Reservation> reservationDateGraterOrEqualThan(Date currentDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(
                root.get("reservationDate"),
                currentDate
        );
    }

    public static Specification<Reservation> guestNameOrRestaurantLike(String searchString) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(
                        root.get("guestName"),
                        "%" + searchString + "%"
                ),
                criteriaBuilder.like(
                        root.get("restaurantName"),
                        "%" + searchString + "%"
                )
        ));
    }
}
