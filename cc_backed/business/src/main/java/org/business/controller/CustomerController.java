package org.business.controller;

import jakarta.servlet.http.HttpSession;
import org.business.service.DelegateCustomerService;
import org.service.customer.pojo.CustomerDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final DelegateCustomerService customerService;
    private final HttpSession httpSession;

    public CustomerController(DelegateCustomerService customerService, HttpSession httpSession) {
        this.customerService = customerService;
        this.httpSession = httpSession;
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

    @GetMapping("/currentCustomer")
    public ResponseEntity<CustomerDto> getCurrentCustomer() {
        Integer customerId = (Integer) httpSession.getAttribute("customerId");
        CustomerDto response = customerService.getCustomerById(customerId);
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
