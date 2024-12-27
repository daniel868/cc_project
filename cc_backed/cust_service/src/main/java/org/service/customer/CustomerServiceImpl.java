package org.service.customer;

import org.service.customer.model.Customer;
import org.service.customer.pojo.CustomerDto;
import org.service.customer.repository.CustomerRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDto> viewCustomers(Pageable pageable) {
        Page<Customer> page = customerRepository.findAll(pageable);
        page.getTotalElements();
        page.getTotalPages();

        return customerRepository.findAll(pageable)
                .stream()
                .map(customer ->
                        CustomerDto.builder()
                                .emailAddress(customer.getEmailAddress())
                                .name(customer.getName())
                                .id(customer.getId())
                                .phoneNumber(customer.getPhoneNumber())
                                .build())
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto newCustomerRequests(CustomerDto customerDto) {
        Customer customer = new Customer();
        mapFromCustomerDtoToEntity(customerDto, customer);

        Customer newCustomer = customerRepository.save(customer);
        customerDto.setId(newCustomer.getId());

        return customerDto;
    }

    @Override
    public boolean manageCustomers(HttpMethod method, Integer customerId, CustomerDto customerDto) {
        if (HttpMethod.DELETE.equals(method)) {
            customerRepository.deleteById(customerId);
            return true;
        }
        Customer customer = customerRepository.findById(customerId)
                .orElse(null);

        if (customer == null) {
            throw new RuntimeException("Could not find customerId with id: " + customerId);
        }

        mapFromCustomerDtoToEntity(customerDto, customer);
        customerRepository.save(customer);

        return true;
    }

    private void mapFromCustomerDtoToEntity(CustomerDto customerDto, Customer customer) {
        customer.setEmailAddress(customerDto.getEmailAddress());
        customer.setName(customerDto.getName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
    }
}
