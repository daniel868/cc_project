package org.business;

import org.business.model.Reservation;
import org.business.model.Restaurant;
import org.business.pojo.ReservationDto;
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
                             ReservationService reservationService) {
        return args -> {
            IntStream.range(0, 20).forEach(i -> {
                Restaurant restaurant1 = new Restaurant();
                restaurant1.setName("restaurant" + i);
                restaurant1.setAddress("address" + i);
                restaurant1.setAvailableSpots(new Random().nextInt(0, 100));
                restaurant1.setImageUrl("https://plus.unsplash.com/premium_photo-1661883237884-263e8de8869b?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8cmVzdGF1cmFudHxlbnwwfHwwfHx8MA%3D%3D");
                restaurant1.setDescription("restaurantDescription" + i);

                if (i < 10) {
                    Reservation reservation = new Reservation();
                    reservation.setGuestCount(i + 10);
                    reservation.setGuestName("guest" + i);
                    reservation.setReservationDate(new Date(System.currentTimeMillis() + (60 * 60 * 1000)));
                    reservation.setRestaurantName("restaurant" + i);
                    restaurant1.addReservation(reservation);
                }

                Restaurant saved = restaurantRepository.save(restaurant1);

            });

        };
    }
}