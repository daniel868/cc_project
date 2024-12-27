package org.business.service;

import org.business.pojo.RestaurantDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.business.model.*;

import java.util.List;

@Service
public interface RestaurantService {

    List<RestaurantDto> showAvailableRestaurants(Pageable pageable);

    RestaurantDto addNewRestaurant(RestaurantDto restaurantDto);

    boolean manageRestaurants(HttpMethod method,
                              Integer restaurantId,
                              RestaurantDto restaurantDto);
}