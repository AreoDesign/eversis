package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.entity.Mission;
import com.eversis.spaceagencydatahub.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MissionRepository extends JpaRepository<Mission, String> {

    @Query(" select p " +
            "from Product p " +
            "where p.mission = :mission ")
    Set<Product> getProducts(@Param("mission") Mission mission);
}
