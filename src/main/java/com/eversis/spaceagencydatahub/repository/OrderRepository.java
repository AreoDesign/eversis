package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserName(String userName);
}
