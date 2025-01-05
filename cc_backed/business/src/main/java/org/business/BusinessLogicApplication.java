package org.business;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.business.model.Reservation;
import org.business.model.Restaurant;
import org.business.pojo.ReservationDto;
import org.business.pojo.RestaurantDto;
import org.business.repository.ReservationRepository;
import org.business.repository.RestaurantRepository;
import org.business.service.ReservationService;
import org.business.utils.PageableResponse;
import org.service.customer.CustomerService;
import org.service.customer.model.Customer;
import org.service.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootApplication
@ComponentScan(basePackages = {"org.service.*", "org.business.*"})
public class BusinessLogicApplication {
    private static final Logger logger = LoggerFactory.getLogger(BusinessLogicApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BusinessLogicApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ReservationRepository reservationRepository,
                             RestaurantRepository restaurantRepository,
                             CustomerRepository customerRepository) {
        return args -> {
            ObjectMapper objectMapper = new ObjectMapper();
            try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("restaurantMock.json")) {
                List<RestaurantDto> jsonMockData = objectMapper.readValue(in, new TypeReference<List<RestaurantDto>>() {
                });

                jsonMockData.forEach(restaurantDto -> {
                    Restaurant restaurant = new Restaurant();
                    restaurant.setAvailableSpots(restaurantDto.getAvailableSpots());
                    restaurant.setName(restaurantDto.getName());
                    restaurant.setImageUrl(restaurantDto.getImageUrl());
                    restaurant.setAddress(restaurantDto.getAddress());
                    restaurant.setDescription(restaurantDto.getDescription());

                    Reservation reservation = new Reservation();
                    reservation.setGuestCount(new Random().nextInt(0, 20));
                    reservation.setGuestName("guestName");
                    reservation.setReservationDate(new Date(System.currentTimeMillis() + (new Random().nextLong(0, 300) * 60 * 60 * 1000)));
                    reservation.setRestaurantName(restaurant.getName());
                    restaurant.addReservation(reservation);

                    restaurantRepository.save(restaurant);
                });
            }

            Customer customer = new Customer();
            customer.setEmailAddress("test@gmail.com");
            customer.setName("test");
            customer.setPhoneNumber("0721311421");

            customerRepository.save(customer);
//
//            });

        };
    }
}