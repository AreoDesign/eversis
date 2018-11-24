package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.entity.OrderProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductDetailsRepository extends JpaRepository<OrderProductDetails, Long> {
}
