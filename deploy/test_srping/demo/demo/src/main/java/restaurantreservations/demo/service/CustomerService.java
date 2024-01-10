package restaurantreservations.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Slf4j
@Service
public class CustomerService implements CustomerServiceInterface {
    @Autowired
  private CustomerRepository customerRepository;


    /*public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }*/


    public List<Customer> findByName(String name) {
        log.info("Getting customer {} from the repository.", name);

        Iterator<Customer> customerIt = customerRepository.findAll().iterator();
        List<Customer> customerList = null;

        for (Iterator<Customer> it = customerIt; it.hasNext(); ) {
            Customer customer = it.next();
            customerList.add(customer);
        }


        if (CollectionUtils.isEmpty(customerList)) {
            log.info("No customer found by name {}", name);
            return new ArrayList<Customer>();
        }
        log.info("found {} customers by name {}", customerList.size(), name);
        return customerList;
    }
}


