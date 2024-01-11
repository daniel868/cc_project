package com.example.business_logic.service;

import com.example.business_logic.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {

    List<Restaurant> getAllRestaurants();

    Restaurant selectRestaurant(String name);
    int getMaxGuestNumber(String name);
    int getAvailableSpots(String name);

    void updateAvailableSpots(String name, int newAvailableSpots);
    Restaurant addRestaurant(int id, String name, String owner, String address, int availableSpots, int maximumGuestNumber);

}
