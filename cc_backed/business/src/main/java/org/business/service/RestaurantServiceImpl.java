package org.business.service;

import org.business.model.*;
import org.business.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        Iterator<Restaurant> restaurantIt = restaurantRepository.findAll().iterator();
        List<Restaurant> restaurantList = new ArrayList<>();

        for (Iterator<Restaurant> it = restaurantIt; it.hasNext(); ) {
            Restaurant restaurant = it.next();
            restaurantList.add(restaurant);
        }

        for (Restaurant restaurant : restaurantList) {
            System.out.println("Got restaurant: " + restaurant.getName());
        }

        return restaurantList;
    }

    @Override
    public Integer getAvailableSpots(Integer re) {
        List<Restaurant> restaurantList = restaurantRepository.findByName(name);


        return 0;
    }

    @Override
    public void updateAvailableSpots(Integer restaurantId,
                                     Integer newAvailableSpots) {
        restaurantRepository.findById(restaurantId)
                .ifPresent(currentRestaurant -> {
                    currentRestaurant.setAvailableSpots(newAvailableSpots);
                    restaurantRepository.save(currentRestaurant);
                });
    }

    @Override
    public Restaurant addRestaurant(int id, String name, String owner, String address, int availableSpots, int maximumGuestNumber, String imageUrl) {
        return restaurantRepository.save(new Restaurant(id, name, owner, address, availableSpots, maximumGuestNumber, imageUrl));
    }

    @Override
    public Long deleteRestaurantByName(String name) {
        return restaurantRepository.deleteByName(name);
    }
}
