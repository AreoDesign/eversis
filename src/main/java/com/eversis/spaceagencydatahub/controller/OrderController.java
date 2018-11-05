package com.eversis.spaceagencydatahub.controller;

import com.eversis.spaceagencydatahub.assembler.OrderAssembler;
import com.eversis.spaceagencydatahub.dictionary.Role;
import com.eversis.spaceagencydatahub.dto.OrderDTO;
import com.eversis.spaceagencydatahub.service.OrderService;
import org.apache.commons.lang3.Validate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;
    private OrderAssembler orderAssembler;

    public OrderController(OrderService orderService, OrderAssembler orderAssembler) {
        this.orderService = orderService;
        this.orderAssembler = orderAssembler;
    }

    @PostMapping("/orders")
    public OrderDTO addOrder(@RequestBody OrderDTO orderDTO){
        Validate.notNull(orderDTO);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        orderDTO.setUserName(auth.getName());
        orderDTO.setId(null);
        return orderService.add(orderDTO);
    }

    @GetMapping("/orders")
    public List<OrderDTO> getOrders(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isManager = auth.getAuthorities().contains(Role.CONTENT_MANAGER);
        return isManager ? orderService.findAllOrders() : orderService.findOrdersForUser(auth.getName());
    }

}
