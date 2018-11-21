package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.dictionary.ImageType;
import com.eversis.spaceagencydatahub.entity.Mission;
import com.eversis.spaceagencydatahub.entity.Product;
import org.apache.commons.math3.util.Precision;
import org.junit.Assert;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.Set;

public class Utils {

    private final static Random random = new Random();

    static Mission createMission(String missionName, Set<Mission> missions, MissionRepository missionRepository) {
        //set up mission
        Mission mission = Mission
                .builder(missionName)
                .imageType(ImageType.values()[random.nextInt(3)])
                .startDate(Instant.now())
                .endDate(Instant.now().plus(random.nextInt(1000), ChronoUnit.DAYS))
                .build();

        //save in db
        mission = missionRepository.save(mission);

        //add to missions container
        missions.add(mission);

        return mission;
    }

    static void removeMission(Mission mission, MissionRepository missionRepository) {
        final long recordCnt = missionRepository.count();
        mission = missionRepository.findById(mission.getName()).get();
        mission.setActive(false);
        mission = missionRepository.save(mission);

        //verification if existing entity was modified and no new record was created by Hibernate on db
        Assert.assertEquals(recordCnt, missionRepository.count());
        Assert.assertNotNull(mission.getDeactivationDate());
    }

    static Product createProductForMission(Mission mission, Set<Product> products, ProductRepository productRepository) {
        //set up products
        Product product = new Product(BigDecimal.valueOf(random.nextInt(200)).setScale(2), "http://space-agency-data-hub.com/images/" + random.nextInt(1000));
        product.setAcquisitionDate(Instant.now());
        product.setFootprintLatitude(Precision.round(random.nextDouble() * 100, 2));
        product.setFootprintLongitude(Precision.round(random.nextDouble() * 100, 2));
        product.setFootprintAltitude(Precision.round(random.nextDouble() * 100, 2));
        product.setFootprintFourthCoordinate(Precision.round(random.nextDouble() * 100, 2));
        product.setMission(mission);

        //save in db
        product = productRepository.save(product);

        //add to products container
        products.add(product);

        return product;
    }

    static void removeProduct(Product product, ProductRepository productRepository) {
        final long recordCnt = productRepository.count();
        product = productRepository.findById(product.getId()).get();
        product.setActive(false);
        product = productRepository.save(product);

        //verification if existing entity was modified and no new record was created by Hibernate on db
        Assert.assertEquals(recordCnt, productRepository.count());
        Assert.assertNotNull(product.getDeactivationDate());
    }
}
