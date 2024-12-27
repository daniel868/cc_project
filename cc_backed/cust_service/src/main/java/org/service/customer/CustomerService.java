package org.service.customer;

import org.service.customer.pojo.CustomerDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> viewCustomers(Pageable pageable);

    CustomerDto newCustomerRequests(CustomerDto customerDto);

    boolean manageCustomers(HttpMethod method,
                            Integer customerId,
                            CustomerDto customerDto);
}
