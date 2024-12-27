package org.business.service;

import org.business.model.*;
import org.business.pojo.RestaurantDto;
import org.business.repository.RestaurantRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<RestaurantDto> showAvailableRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable)
                .stream()
                .map(restaurant -> RestaurantDto.builder()
                        .address(restaurant.getAddress())
                        .name(restaurant.getName())
                        .availableSpots(restaurant.getAvailableSpots())
                        .imageUrl(restaurant.getImageUrl())
                        .id(restaurant.getId())
                        .build()
                ).toList();
    }

    @Override
    public RestaurantDto addNewRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();
        mapFromRestaurantToDto(restaurantDto, restaurant);

        Restaurant newRestaurant = restaurantRepository.save(restaurant);
        restaurantDto.setId(newRestaurant.getId());

        return restaurantDto;
    }

    @Override
    public boolean manageRestaurants(HttpMethod method,
                                     Integer restaurantId,
                                     RestaurantDto restaurantDto) {
        if (HttpMethod.DELETE.equals(method)) {
            restaurantRepository.deleteById(restaurantId);
            return true;
        }

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElse(null);
        if (restaurant == null) {
            throw new RuntimeException("Could not find restaurant with id: " + restaurantId);
        }
        mapFromRestaurantToDto(restaurantDto, restaurant);
        restaurantRepository.save(restaurant);

        return true;
    }

    private void mapFromRestaurantToDto(RestaurantDto restaurantDto, Restaurant restaurant) {
        restaurant.setAvailableSpots(restaurantDto.getAvailableSpots());
        restaurant.setImageUrl(restaurantDto.getImageUrl());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setName(restaurantDto.getName());
    }
}
