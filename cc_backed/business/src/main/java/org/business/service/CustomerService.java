package org.business.service;

import org.business.model.*;
import org.business.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findByName(String name) {
        System.out.println("Getting customer {} from the repository" + name);

        Iterator<Customer> customerIt = customerRepository.findAll().iterator();
        List<Customer> customerList = new ArrayList<>();

        for (Iterator<Customer> it = customerIt; it.hasNext(); ) {
            Customer customer = it.next();
            customerList.add(customer);
        }


        if (CollectionUtils.isEmpty(customerList)) {
            return new ArrayList<Customer>();
        }

        return customerRepository.findByName(name);
    }

    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<>();
        for (Iterator<Customer> it = customerRepository.findAll().iterator(); it.hasNext(); ) {
            Customer customer = it.next();
            customerList.add(customer);
        }

        for (Customer c : customerList) {
            System.out.println(c.getName());
        }

        return customerList;
    }
}
