package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.entity.OrderStatusDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusDetailsRepository extends JpaRepository<OrderStatusDetails, Long> {
}
