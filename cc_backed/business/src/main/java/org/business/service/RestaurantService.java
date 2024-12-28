package org.business.service;

import org.business.pojo.RestaurantDto;
import org.business.utils.PageableResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.business.model.*;

import java.util.List;

@Service
public interface RestaurantService {

    PageableResponse<RestaurantDto> showAvailableRestaurants(Pageable pageable, String searchString, Integer guestCountFilter);

    RestaurantDto addNewRestaurant(RestaurantDto restaurantDto);

    boolean manageRestaurants(HttpMethod method,
                              Integer restaurantId,
                              RestaurantDto restaurantDto);
}