package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
