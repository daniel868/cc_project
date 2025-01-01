package org.business.controller;

import org.business.service.DelegateCustomerService;
import org.service.customer.pojo.CustomerDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final DelegateCustomerService customerService;

    public CustomerController(DelegateCustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public List<CustomerDto> getAllCustomers(Pageable pageable) {
        return customerService.viewCustomers(pageable);
    }

    @PostMapping("")
    public ResponseEntity<CustomerDto> addNewCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto response = customerService.newCustomerRequests(customerDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Boolean> updateCustomer(@PathVariable("customerId") Integer customerId,
                                                  @RequestBody CustomerDto customerDto) {
        boolean response = customerService.manageCustomers(HttpMethod.PUT, customerId, customerDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Boolean> deleteCustomer(@PathVariable("customerId") Integer customerId) {
        boolean response = customerService.manageCustomers(HttpMethod.DELETE, customerId, null);
        return ResponseEntity.ok(response);
    }
}
