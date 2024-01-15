package com.example.business_logic.service;

import com.example.business_logic.model.Restaurant;
import com.example.business_logic.repository.RestaurantRepository;
import com.example.business_logic.service.RestaurantService;
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
        Iterator<Restaurant> restaurantIt= restaurantRepository.findAll().iterator();
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
    public Restaurant selectRestaurant(String name) {
        List<Restaurant> restaurantList = restaurantRepository.findByName(name);

        for(Restaurant restaurant : restaurantList) {
            if (restaurant.getName().equals(name)) {
                return restaurant;
            }
        }

        return null;
    }

    @Override
    public int getMaxGuestNumber(String name) {
        List<Restaurant> restaurantList = restaurantRepository.findByName(name);

        for(Restaurant restaurant : restaurantList) {
            if (restaurant.getName().equals(name)) {
                return restaurant.getMaximumGuestNumber();
            }
        }

        return 0;
    }

    @Override
    public int getAvailableSpots(String name) {
        List<Restaurant> restaurantList = restaurantRepository.findByName(name);

        for(Restaurant restaurant : restaurantList) {
            if (restaurant.getName().equals(name)) {
                return restaurant.getAvailableSpots();
            }
        }

        return 0;
    }

    @Override
    public void updateAvailableSpots(String name, int newAvailableSpots) {
        List<Restaurant> restaurantList = restaurantRepository.findByName(name);

        for(Restaurant restaurant : restaurantList) {
            if (restaurant.getName().equals(name)) {
                restaurant.setAvailableSpots(newAvailableSpots);
                restaurantRepository.save(restaurant);
                break;
            }
        }

    }

    @Override
    public Restaurant addRestaurant(int id, String name, String owner, String address, int availableSpots, int maximumGuestNumber) {
      return restaurantRepository.save(new Restaurant(id, name, owner, address, availableSpots, maximumGuestNumber));
    }

    @Override
    public Long deleteRestaurantByName(String name) {
        return restaurantRepository.deleteByName(name);
    }

}
