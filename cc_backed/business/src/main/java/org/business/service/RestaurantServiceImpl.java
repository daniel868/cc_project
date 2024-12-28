package org.business.service;

import ch.qos.logback.core.util.StringUtil;
import org.business.model.*;
import org.business.pojo.RestaurantDto;
import org.business.repository.RestaurantRepository;
import org.business.utils.AppUtils;
import org.business.utils.PageableResponse;
import org.springframework.data.domain.Page;
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
    public PageableResponse<RestaurantDto> showAvailableRestaurants(Pageable pageable, String searchString, Integer guestCountFilter) {
        pageable = AppUtils.enhancePageable(pageable);
        Page<Restaurant> restaurantPage = buildRestaurantSearchQuery(pageable, searchString, guestCountFilter);

        List<RestaurantDto> payload = restaurantPage.stream()
                .map(restaurant -> RestaurantDto.builder()
                        .address(restaurant.getAddress())
                        .name(restaurant.getName())
                        .availableSpots(restaurant.getAvailableSpots())
                        .imageUrl(restaurant.getImageUrl())
                        .id(restaurant.getId())
                        .build()
                ).toList();

        return AppUtils.buildPageableResponse(payload, restaurantPage);
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

    private Page<Restaurant> buildRestaurantSearchQuery(Pageable pageable, String searchString, Integer guestCountFilter) {
        boolean emptySearchString = StringUtil.isNullOrEmpty(searchString);
        boolean emptyGuestCountFilter = (guestCountFilter == null || guestCountFilter.equals(-1));

        if (!emptySearchString && !emptyGuestCountFilter) {
            return restaurantRepository.searchRestaurantByAvailableSpotsGreaterThanAndNameLike(guestCountFilter, searchString, pageable);
        }

        if (emptySearchString && !emptyGuestCountFilter) {
            return restaurantRepository.searchRestaurantByAvailableSpotsGreaterThan(guestCountFilter, pageable);
        }

        if (!emptySearchString) {
            return restaurantRepository.searchRestaurantByNameOrAddressLike(searchString, pageable);
        }

        return restaurantRepository.findAll(pageable);
    }

    private void mapFromRestaurantToDto(RestaurantDto restaurantDto, Restaurant restaurant) {
        restaurant.setAvailableSpots(restaurantDto.getAvailableSpots());
        restaurant.setImageUrl(restaurantDto.getImageUrl());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setName(restaurantDto.getName());
    }
}
