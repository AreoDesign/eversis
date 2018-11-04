package com.eversis.spaceagencydatahub.assembler;

import com.eversis.spaceagencydatahub.dto.OrderDTO;
import com.eversis.spaceagencydatahub.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderAssembler {
    public Order convert(OrderDTO orderDTO) {
        Order order = null;
        if (Objects.nonNull(orderDTO)) {
            order = Order.builder()
                         .id(orderDTO.getId())
                         .productId(orderDTO.getProductId())
                         .userName(orderDTO.getUserName())
                         .build();
        }

        return order;
    }

    public OrderDTO convert(Order orderEntity) {
        OrderDTO orderDTO = null;
        if (Objects.nonNull(orderEntity)) {
            orderDTO = OrderDTO.builder()
                               .id(orderEntity.getId())
                               .productId(orderEntity.getProductId())
                               .userName(orderEntity.getUserName())
                               .build();
        }

        return orderDTO;
    }
}
