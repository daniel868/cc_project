package org.business;

import org.business.model.Restaurant;
import org.business.repository.RestaurantRepository;
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

@SpringBootApplication
@ComponentScan(basePackages = {"org.service.*", "org.business.*"})
public class BusinessLogicApplication {
    private static final Logger logger = LoggerFactory.getLogger(BusinessLogicApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BusinessLogicApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {

        };
    }
}