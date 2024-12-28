package org.business.repository;

import org.business.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

public class RestaurantSpecification {
    public static Specification<Restaurant> hasAvailableSpotsGreaterThan(int availableSpots) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(
                root.get("availableSpots"), availableSpots
        );
    }

    public static Specification<Restaurant> nameOrAddressLike(String searchString) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(
                        root.get("name"), "%" + searchString + "%"
                ),
                criteriaBuilder.like(
                        root.get("address"), "%" + searchString + "%"
                )
        );
    }
}
