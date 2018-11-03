package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.dictionary.ImageType;
import com.eversis.spaceagencydatahub.entity.Mission;
import com.eversis.spaceagencydatahub.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ProductRepositoryTest {

    @Autowired
    private final MissionRepository missionRepository = null;

    @Autowired
    private final ProductRepository productRepository = null;

    private Set<Mission> missions = Sets.newHashSet();
    private Set<Product> products = Sets.newHashSet();

    @After
    public void clean() {
        productRepository.deleteAll(products);
        missionRepository.deleteAll(missions);
    }

    @Test
    public void readWriteTest() {
        Mission mission = createMission();
        Product product = createProduct(mission);

        Assert.assertNotNull(product.getMission());

        //refresh Entity from DB
        mission = missionRepository.findById(mission.getName()).get();

        List<Product> missionProducts = mission.getProducts();

        log.info("mission.getProducts(): {}", missionProducts.toString());

        Assert.assertNotNull(mission.getProducts());
        Assert.assertEquals(1, mission.getProducts().size());

        Assert.assertEquals(mission, product.getMission());
    }

    private Mission createMission() {
        //set up mission
        Mission mission = Mission
                .builder("Calypso")
                .imageType(ImageType.HYPERSPECTRAL)
                .startDate(Instant.now())
                .endDate(Instant.now().plus(365, ChronoUnit.DAYS))
                .build();

        //save in db
        mission = missionRepository.saveAndFlush(mission);

        //add to missions container
        missions.add(mission);

        return mission;
    }

    private Product createProduct(Mission mission) {
        //set up products
        Product product = new Product(BigDecimal.valueOf(100L), "http://space-agency-data-hub.com/images/1");
        product.setAquisitionDate(Instant.now());
        product.setFootprintLatitude(30.3d);
        product.setFootprintLongitude(10.9d);
        product.setFootprintAltitude(1000d);
        product.setFootprintFourthAngle(1.5d);
        product.setMission(mission);

        //save in db
        product = productRepository.saveAndFlush(product);

        //add to products container
        products.add(product);

        return product;
    }
}