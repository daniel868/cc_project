package restaurantreservations.demo.service;

import java.util.List;

public interface CustomerServiceInterface {
    List<Customer> findByName(String name);
}
