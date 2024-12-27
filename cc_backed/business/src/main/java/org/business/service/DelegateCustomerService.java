package org.business.service;

import org.service.customer.model.Customer;
import org.service.customer.pojo.CustomerDto;
import org.service.customer.repository.CustomerRepository;
import org.service.customer.CustomerService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelegateCustomerService {
    private final CustomerService customerService;

    public DelegateCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<CustomerDto> viewCustomers(Pageable pageable) {
        return customerService.viewCustomers(pageable);
    }

    public CustomerDto newCustomerRequests(CustomerDto customerDto) {
        return customerService.newCustomerRequests(customerDto);
    }

    public boolean manageCustomers(HttpMethod method,
                                   Integer customerId,
                                   CustomerDto customerDto) {
        return customerService.manageCustomers(method, customerId, customerDto);
    }
}
