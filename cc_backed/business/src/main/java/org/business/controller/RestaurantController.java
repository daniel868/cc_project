package org.business.controller;

import org.business.model.*;
import org.business.pojo.RestaurantDto;
import org.business.service.RestaurantService;
import org.business.utils.PageableResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("")
    public PageableResponse<RestaurantDto> getAllRestaurants(Pageable pageable,
                                                             @RequestParam(value = "searchString", required = false) String searchString,
                                                             @RequestParam(value = "guestCountFilter", required = false) Integer guestCountFilter) {
        return restaurantService.showAvailableRestaurants(pageable, searchString, guestCountFilter);
    }

    @PostMapping(path = "")
    public ResponseEntity<RestaurantDto> addNewRestaurant(@RequestBody RestaurantDto restaurantDto) {
        RestaurantDto response = restaurantService.addNewRestaurant(restaurantDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Boolean> updateRestaurant(@RequestBody RestaurantDto restaurantDto,
                                                    @PathVariable("restaurantId") Integer restaurantId) {
        boolean response = restaurantService.manageRestaurants(HttpMethod.PUT, restaurantId, restaurantDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Boolean> deleteRestaurant(@PathVariable("restaurantId") Integer restaurantId) {
        boolean response = restaurantService.manageRestaurants(HttpMethod.DELETE, restaurantId, null);
        return ResponseEntity.ok(response);
    }
}
