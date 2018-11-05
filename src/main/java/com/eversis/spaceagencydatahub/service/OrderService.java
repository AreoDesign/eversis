package com.eversis.spaceagencydatahub.service;

import com.eversis.spaceagencydatahub.assembler.OrderAssembler;
import com.eversis.spaceagencydatahub.dto.OrderDTO;
import com.eversis.spaceagencydatahub.entity.Order;
import com.eversis.spaceagencydatahub.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private OrderAssembler orderAssembler;

    public OrderService(OrderRepository orderRepository, OrderAssembler orderAssembler) {
        this.orderRepository = orderRepository;
        this.orderAssembler = orderAssembler;
    }

    public OrderDTO add(OrderDTO orderDTO) {
        Order orderEntity = orderRepository.save(orderAssembler.convert(orderDTO));
        return orderAssembler.convert(orderEntity);
    }

    public List<OrderDTO> findAllOrders() {
        return orderRepository.findAll()
                       .stream()
                       .map(orderAssembler::convert)
                       .collect(Collectors.toList());
    }

    public List<OrderDTO> findOrdersForUser(String username) {
        return orderRepository.findByUserName(username)
                              .stream()
                              .map(orderAssembler::convert)
                              .filter(Objects::nonNull)
                              .collect(Collectors.toList());
    }
}
