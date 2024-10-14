package org.business.repository;

import org.business.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
    List<Restaurant> findByName(String name);

    Long deleteByName(String name);
}
