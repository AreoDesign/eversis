package com.eversis.spaceagencydatahub.assembler;

import com.eversis.spaceagencydatahub.dto.OrderDTO;
import com.eversis.spaceagencydatahub.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

// TODO: 2018-11-20 clean entity builders
@Component
public class OrderAssembler {

    private CustomerAssembler customerAssembler;

    public OrderAssembler(CustomerAssembler customerAssembler) {
        this.customerAssembler = customerAssembler;
    }

    public Order convert(OrderDTO orderDTO) {
        Order order = null;
        if (Objects.nonNull(orderDTO)) {
            order = Order.builder()
                         .id(orderDTO.getId())
                         .customer(customerAssembler.convert(orderDTO.getCustomerDTO()))
                         .build();
        }

        return order;
    }

    public OrderDTO convert(Order orderEntity) {
        OrderDTO orderDTO = null;
        if (Objects.nonNull(orderEntity)) {
            orderDTO = OrderDTO.builder()
                               .id(orderEntity.getId())
                               .customerDTO(customerAssembler.convert(orderEntity.getCustomer()))
                               .build();
        }

        return orderDTO;
    }
}
