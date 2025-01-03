package org.service.customer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.service.customer.model.Customer;
import org.service.customer.pojo.CustomerDto;
import org.service.customer.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final RestClient restClient;
    private final HttpServletRequest request;

    public CustomerServiceImpl(CustomerRepository customerRepository, RestClient restClient, HttpServletRequest request) {
        this.customerRepository = customerRepository;
        this.restClient = restClient;
        this.request = request;
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

        String authorization = request.getHeader("Authorization");
        String jwtToken = authorization.substring(7);

        ResponseEntity response = restClient.post()
                .uri("/user/" + customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(customerDto)
                .retrieve()
                .toBodilessEntity();

        if (response.getStatusCode() != HttpStatusCode.valueOf(200)) {
            throw new RuntimeException("Could not success update customer with id: "+customerId);
        }
        customerRepository.save(customer);

        return true;
    }

    @Override
    public CustomerDto loadCustomerById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElse(null);
        if (customer == null) {
            throw new RuntimeException("Could not find customer with id: " + customerId);
        }
        return CustomerDto.builder()
                .id(customerId)
                .emailAddress(customer.getEmailAddress())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }

    private void mapFromCustomerDtoToEntity(CustomerDto customerDto, Customer customer) {
        customer.setEmailAddress(customerDto.getEmailAddress());
        customer.setName(customerDto.getName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
    }
}
