package com.example.business_logic.repository;

import com.example.business_logic.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
    List<Restaurant> findByName(String name);
}
