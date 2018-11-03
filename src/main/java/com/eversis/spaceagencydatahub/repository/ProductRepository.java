package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
