package org.business.controller;

import org.business.model.*;
import org.business.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {
    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/{name}")
    public List<Customer> getCustomerByName(@PathVariable String name) {
        System.out.println("Getting customer by name " + name);
        List customerList = customerService.findByName(name);
        System.out.println("Received customers by name " + customerList.size() + " " + name);
        return customerList;
    }

    @GetMapping("/customers/")
    public List<Customer> getAllCustomers() {
        System.out.println("Getting all customer by name ");
        List customerList = customerService.findAll();
        return customerList;
    }

}
