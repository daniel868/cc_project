package org.service.customer.repository;

import org.service.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "select c from Customer c where c.name like  %:searchString% or c.emailAddress like %:searchString%")
    Page<Customer> searchCustomersWithParams(@Param("searchString") String searchString, Pageable pageable);
}
