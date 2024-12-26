package org.business.repository;

import org.business.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
    Optional<Restaurant> findRestaurantByName(String name);

    Long deleteByName(String name);
}
