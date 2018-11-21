package com.eversis.spaceagencydatahub.service;

import com.eversis.spaceagencydatahub.assembler.CustomerAssembler;
import com.eversis.spaceagencydatahub.dto.CustomerDTO;
import com.eversis.spaceagencydatahub.entity.Customer;
import com.eversis.spaceagencydatahub.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
public class CustomerService {

    private CustomerAssembler customerAssembler;
    private CustomerRepository customerRepository;

    public CustomerService(CustomerAssembler customerAssembler) {
        this.customerAssembler = customerAssembler;
    }

    public Customer getCustomer(String login) {
        Customer customer = customerRepository.findById(login)
                                              .orElseThrow(() -> {
                                                  String errMsg = String.format("Customer with login: %s not found.", login);
                                                  log.error(errMsg);
                                                  return new EntityNotFoundException(errMsg);
                                              });
        return customer;
    }

    public CustomerDTO getCustomerDTO(String login) {
        return customerAssembler.convert(this.getCustomer(login));
    }
}
