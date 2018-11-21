package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(" select o " +
            "from Order o " +
            "where o.customer.login = :givenLogin ")
    List<Order> findByLogin(@Param("givenLogin") String login);// TODO: 2018-11-20 check if it works?!
}
