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
    public void readWriteDeleteTest() {
        Mission mission = createMission();
        Product product = createProduct(mission);

        Assert.assertNotNull(product.getMission());

        //refresh Entity from DB
        mission = missionRepository.findById(mission.getName()).get();

        Assert.assertEquals(mission, product.getMission());

        removeMission(mission);

        Assert.assertEquals(false, missionRepository.findById(mission.getName()).get().isActive());
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

    private void removeMission(Mission mission){
        final long recordCnt = missionRepository.count();
        Mission missionInner = missionRepository.findById(mission.getName()).get();
        Assert.assertEquals(mission.getName(), missionInner.getName());
        missionInner.setActive(false);
        missionRepository.save(missionInner);
        //verification if existing entity was modified and no new record was created by Hibernate on db
        Assert.assertEquals(recordCnt, missionRepository.count());
    }

    private Product createProduct(Mission mission) {
        //set up products
        Product product = new Product(BigDecimal.valueOf(100L), "http://space-agency-data-hub.com/images/1");
        product.setAquisitionDate(Instant.now());
        product.setFootprintLatitude(30.3d);
        product.setFootprintLongitude(10.9d);
        product.setFootprintAltitude(1000d);
        product.setFootprintFourthCoordinate(1.5d);
        product.setMission(mission);
        //save in db
        product = productRepository.saveAndFlush(product);
        //add to products container
        products.add(product);

        return product;
    }
}