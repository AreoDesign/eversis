package com.eversis.spaceagencydatahub.assembler;

import com.eversis.spaceagencydatahub.dto.CustomerDTO;
import com.eversis.spaceagencydatahub.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomerAssembler {

    public Customer convert(CustomerDTO customerDTO) {
        Customer customer = null;
        if (Objects.nonNull(customerDTO)) {
            customer = Customer.builder()
                               .login(customerDTO.getLogin())
                               .password(customerDTO.getPassword())
                               .role(customerDTO.getRole())
                               .build();
        }

        return customer;
    }

    public CustomerDTO convert(Customer customer) {
        CustomerDTO customerDTO = null;
        if (Objects.nonNull(customer)) {
            customerDTO = CustomerDTO.builder()
                    .login(customer.getLogin())
                    .password(customer.getPassword())
                    .role(customer.getRole())
                    .build();
        }

        return customerDTO;
    }
}
