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

import java.util.Date;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = {"org.service.*", "org.business.*"})
public class BusinessLogicApplication {
    private static final Logger logger = LoggerFactory.getLogger(BusinessLogicApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BusinessLogicApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ReservationRepository reservationRepository,
                             ReservationService reservationService) {
        return args -> {
            Reservation reservation = new Reservation();
            reservation.setRestaurantName("restaurant1");
            reservation.setReservationDate(new Date());
            reservation.setGuestCount(10);
            reservation.setGuestName("guestName");

            Reservation reservation2 = new Reservation();
            reservation.setRestaurantName("restaurant2");
            reservation.setReservationDate(new Date());
            reservation.setGuestCount(10);
            reservation.setGuestName("guestName2");

            reservationRepository.saveAll(List.of(reservation, reservation2));

            PageableResponse<ReservationDto> response = reservationService.findReservations(PageRequest.of(0, 1), "Name2");
            logger.info("");
        };
    }
}