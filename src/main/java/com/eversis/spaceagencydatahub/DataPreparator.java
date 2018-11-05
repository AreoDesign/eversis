package com.eversis.spaceagencydatahub;

import com.eversis.spaceagencydatahub.dictionary.ImageType;
import com.eversis.spaceagencydatahub.entity.Mission;
import com.eversis.spaceagencydatahub.entity.Product;
import com.eversis.spaceagencydatahub.repository.MissionRepository;
import com.eversis.spaceagencydatahub.repository.ProductRepository;
import com.google.common.collect.Sets;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Random;

@Component
public class DataPreparator {

    private MissionRepository missionRepository;
    private ProductRepository productRepository;

    public DataPreparator(MissionRepository missionRepository, ProductRepository productRepository) {
        this.missionRepository = missionRepository;
        this.productRepository = productRepository;
    }

    static Random random = new Random();

    public void prepare() {
        Mission missionOne = createMission("Andromeda", ImageType.HYPERSPECTRAL);
        Mission missionTwo = createMission("Calliope", ImageType.MULTISPECTRAL);
        Mission missionThree = createMission("Odys", ImageType.PANCHROMATIC);
        missionRepository.saveAll(Sets.newHashSet(missionOne, missionTwo, missionThree));
        Product productOne = createProduct(random.nextInt(100) + 1, "http://space-agency-data-hub.com/images/1", missionOne);
        Product productTwo = createProduct(random.nextInt(100) + 1, "http://space-agency-data-hub.com/images/2", missionOne);
        Product productThree = createProduct(random.nextInt(100) + 1, "http://space-agency-data-hub.com/images/3", missionTwo);
        Product productFour = createProduct(random.nextInt(100) + 1, "http://space-agency-data-hub.com/images/4", missionTwo);
        Product productFive = createProduct(random.nextInt(100) + 1, "http://space-agency-data-hub.com/images/5", missionThree);
        Product productSix = createProduct(random.nextInt(100) + 1, "http://space-agency-data-hub.com/images/6", missionThree);
        Product productSeven = createProduct(random.nextInt(100) + 1, "http://space-agency-data-hub.com/images/7", missionThree);
        productRepository.saveAll(Sets.newHashSet(productOne, productTwo, productThree, productFour, productFive,
                productSix, productSeven));
    }

    private static Mission createMission(String missionName, ImageType imageType) {
        return Mission.builder(missionName)
                      .imageType(imageType)
                      .startDate(Instant.now())
                      .endDate(null)
                      .build();
    }

    private static Product createProduct(Integer price, String url, Mission mission) {
        Product product = new Product(BigDecimal.valueOf(price), url);
        product.setAquisitionDate(Instant.now());
        product.setFootprintLatitude(Precision.round(random.nextDouble() * 100, 2));
        product.setFootprintLongitude(Precision.round(random.nextDouble() * 100, 2));
        product.setFootprintAltitude(Precision.round(random.nextDouble() * 100, 2));
        product.setFootprintFourthCoordinate(Precision.round(random.nextDouble() * 100, 2));
        product.setMission(mission);
        return product;
    }
}
