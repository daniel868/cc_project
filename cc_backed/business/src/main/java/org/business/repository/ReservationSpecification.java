package org.business.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.business.model.Reservation;
import org.service.customer.model.Customer;
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
                        criteriaBuilder.lower(root.get("guestName")),
                        "%" + searchString.toLowerCase() + "%"
                ),
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("restaurantName")),
                        "%" + searchString.toLowerCase() + "%"
                )
        ));
    }

    public static Specification<Reservation> withCustomerId(Integer customerId) {
        return ((root, query, criteriaBuilder) -> {
            Join<Reservation, Customer> customer = root.join("customer", JoinType.LEFT);
            return criteriaBuilder.equal(customer.get("id"), customerId);
        });
    }
}
