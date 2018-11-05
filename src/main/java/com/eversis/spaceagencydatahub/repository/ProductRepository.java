package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select  p.id," +
            "               p.mission_name " +
            "               p.aquisition_date " +
            "               p.footprint_latitude " +
            "               p.footprint_longitude " +
            "               p.footprint_altitude " +
            "               p.footprint_time " +
            "               p.price " +
            "               p.url " +
            "               p.is_active " +
            "               p.deactivation_date " +
            "       from " +
            "           product p " +
            "       join " +
            "           product2user p2u on p.id = p2u.product_id " +
            "       where " +
            "           p2u.user_name = :authName ", nativeQuery = true)
    List<Tuple> findAllProductsForCustomerName(@Param("authName") String customerAuthenticationName);

    //FIXME: optimize this solution
    //not ready to work -> need Entity relations
//    @Query("  select new com.eversis.spaceagencydatahub.dto.ProductDTO " +
//            " from Order o " +
//            "     join o. " +
//            " where o.user_name = :authName ")
//    List<ProductDTO> findAllProductsForCustomerName(@Param("authName") String customerAuthenticationName);
}

