package org.business.controller;

import org.business.model.*;
import org.business.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestaurantController {
    final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurants() {
        System.out.println("Getting all restaurants ");
        List restaurantList = restaurantService.getAllRestaurants();
        System.out.println("Received all restaurants " + restaurantList.size());
        return restaurantList;
    }

    @PostMapping(path = "/restaurants/add")
    public ResponseEntity<Restaurant> addNewRestaurant(@RequestBody Restaurant newRestaurant) {
        Restaurant restaurant = restaurantService.addRestaurant(newRestaurant.getId(), newRestaurant.getName(), newRestaurant.getOwner(), newRestaurant.getAddress(), newRestaurant.getAvailableSpots(), newRestaurant.getMaximumGuestNumber(), newRestaurant.getImageUrl());
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PostMapping("/restaurants/{name}/delete")
    public Restaurant deleteRestaurantByName(@PathVariable String name) {
        System.out.println("Getting restaurant by name " + name);
        Restaurant restaurant = restaurantService.selectRestaurant(name);
        System.out.println("Received restaurant by name " + restaurant.getName());
        return restaurant;
    }
}
