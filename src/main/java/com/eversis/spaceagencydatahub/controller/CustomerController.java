package com.eversis.spaceagencydatahub.controller;

import com.eversis.spaceagencydatahub.dictionary.Role;
import com.eversis.spaceagencydatahub.dto.CustomerDTO;
import com.eversis.spaceagencydatahub.service.CustomerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    Set<CustomerDTO> getCustomers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isManager = auth.getAuthorities().contains(Role.CONTENT_MANAGER);

        return isManager ? customerService.getAllCustomers() : null;
    }

//    @PostMapping("/customers")
//    CustomerDTO registerCustomer(@RequestBody CustomerDTO customerDTO){
//        return customerService.register(customerDTO);
//    }
}
