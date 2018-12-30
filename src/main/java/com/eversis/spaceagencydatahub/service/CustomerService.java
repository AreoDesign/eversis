package com.eversis.spaceagencydatahub.service;

import com.eversis.spaceagencydatahub.assembler.CustomerAssembler;
import com.eversis.spaceagencydatahub.dto.CustomerDTO;
import com.eversis.spaceagencydatahub.dto.MissionDTO;
import com.eversis.spaceagencydatahub.entity.Customer;
import com.eversis.spaceagencydatahub.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerService {

    private CustomerAssembler customerAssembler;
    private CustomerRepository customerRepository;

    public CustomerService(CustomerAssembler customerAssembler, CustomerRepository customerRepository) {
        this.customerAssembler = customerAssembler;
        this.customerRepository = customerRepository;
    }

    public Set<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customerAssembler::convert).collect(Collectors.toSet());
    }

    public CustomerDTO getCustomer(String login) {
        Customer customer = customerRepository.findById(login)
                                              .orElseThrow(() -> {
                                                  String errMsg = String.format("Customer with login: %s not found.", login);
                                                  log.error(errMsg);
                                                  return new EntityNotFoundException(errMsg);
                                              });
        return customerAssembler.convert(customer);
    }

    //FIXME: need to change registration process with Spring Security - example at https://www.baeldung.com/registration-restful-api
    public CustomerDTO register(CustomerDTO customerDTO) {
        Validate.notNull(customerDTO, "New customer cannot be null!");
        throwExceptionWhenCustomerExists(customerDTO);
        Customer customerEntity = customerAssembler.convert(customerDTO);
        customerEntity = customerRepository.save(customerEntity);

        return customerAssembler.convert(customerEntity);
    }

    //***************************************************************
    //******************* PRIVATE METHODS SECTION *******************
    //***************************************************************
    private void throwExceptionWhenCustomerExists(CustomerDTO customerDTO) {
        boolean customerExistOnDb = customerRepository.findById(customerDTO.getLogin()).isPresent();

        if (customerExistOnDb) {
            String errMsg = String.format("Customer login '%s', already exists.", customerDTO.getLogin());
            log.error(errMsg);
            throw new EntityExistsException(errMsg);
        }
    }
}
