package org.business;

import org.business.model.Restaurant;
import org.business.repository.RestaurantRepository;
import org.service.customer.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.service.*", "org.business.*"})
public class BusinessLogicApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessLogicApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(RestaurantRepository restaurantRepository) {
        return args -> {
            Restaurant restaurant = new Restaurant();
            restaurant.setAddress("test");
            restaurant.setAvailableSpots(10);
            restaurant.setImageUrl("https://cdn.vox-cdn.com/thumbor/qVw98okiGmZ4-v2EypqK7ObXMKc=/0x0:6240x4160/1200x900/filters:focal(2621x1581:3619x2579):no_upscale()/cdn.vox-cdn.com/uploads/chorus_image/image/62582192/IMG_2025.280.jpg");
            restaurant.setName("test");
            restaurantRepository.save(restaurant);
        };
    }
}