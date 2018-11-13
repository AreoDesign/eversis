package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.entity.Mission;
import com.eversis.spaceagencydatahub.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static com.eversis.spaceagencydatahub.repository.Utils.createMission;
import static com.eversis.spaceagencydatahub.repository.Utils.createProductForMission;
import static com.eversis.spaceagencydatahub.repository.Utils.removeProduct;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ProductRepositoryTest {

    @Autowired
    private final MissionRepository missionRepository = null;

    @Autowired
    private final ProductRepository productRepository = null;

    private long missionsCnt;
    private long productCnt;
    private Set<Mission> missions = Sets.newHashSet();
    private Set<Product> products = Sets.newHashSet();

    @Before
    public void init() {
        missionsCnt = missionRepository.count();
        productCnt = productRepository.count();
    }

    @After
    public void clean() {
        productRepository.deleteAll(products);
        missionRepository.deleteAll(missions);
        assertEquals(missionRepository.count(), missionsCnt);
        assertEquals(productRepository.count(), productCnt);
    }

    @Test
    public void readWriteDeleteTest() {
        Mission mission = createMission("Zephyr", missions, missionRepository);
        Product product = createProductForMission(mission, products, productRepository);
        Set<Product> missionProducts = missionRepository.getProducts(mission);

//        verification if mission and product are successfully stored in db
        assertEquals(missionsCnt + 1, missionRepository.count());
        assertEquals(productCnt + 1, productRepository.count());

//        verification if product is correctly linked to a mission
        assertNotNull(product.getMission());
        assertEquals(mission, product.getMission());

        assertEquals(1, missionProducts.size());
        missionProducts.stream().forEach(r -> {
            assertEquals(product.getId(), r.getId());
            assertEquals(mission, r.getMission());
            assertEquals(product.getAquisitionDate(), r.getAquisitionDate());
            assertEquals(product.getFootprintLatitude(), r.getFootprintLatitude());
            assertEquals(product.getFootprintLongitude(), r.getFootprintLongitude());
            assertEquals(product.getFootprintAltitude(), r.getFootprintAltitude());
            assertEquals(product.getFootprintFourthCoordinate(), r.getFootprintFourthCoordinate());
            assertEquals(product.getPrice(), r.getPrice());
            assertEquals(product.getUrl(), r.getUrl());
            assertEquals(product.isActive(), r.isActive());
            assertEquals(product.getDeactivationDate(), r.getDeactivationDate());
        });

        removeProduct(product, productRepository);
        assertFalse(productRepository.findById(product.getId()).get().isActive());
    }
}