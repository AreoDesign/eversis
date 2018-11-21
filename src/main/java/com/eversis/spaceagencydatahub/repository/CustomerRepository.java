package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    // TODO: 2018-11-20 add methods
}
