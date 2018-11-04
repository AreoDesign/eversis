package com.eversis.spaceagencydatahub;

import com.eversis.spaceagencydatahub.dictionary.ImageType;
import com.eversis.spaceagencydatahub.entity.Mission;
import com.eversis.spaceagencydatahub.entity.Product;
import com.eversis.spaceagencydatahub.repository.MissionRepository;
import com.eversis.spaceagencydatahub.repository.ProductRepository;
import com.google.common.collect.Sets;
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
        Product productOne = createProduct(10, "http://space-agency-data-hub.com/images/1", missionOne);
        Product productTwo = createProduct(20, "http://space-agency-data-hub.com/images/2", missionOne);
        Product productThree = createProduct(25, "http://space-agency-data-hub.com/images/3", missionTwo);
        Product productFour = createProduct(35, "http://space-agency-data-hub.com/images/4", missionTwo);
        Product productFive = createProduct(35, "http://space-agency-data-hub.com/images/5", missionThree);
        Product productSix = createProduct(40, "http://space-agency-data-hub.com/images/6", missionThree);
        Product productSeven = createProduct(49, "http://space-agency-data-hub.com/images/7", missionThree);
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
        product.setFootprintLatitude(random.nextDouble());
        product.setFootprintLongitude(random.nextDouble());
        product.setFootprintAltitude(random.nextDouble());
        product.setFootprintFourthCoordinate(random.nextDouble());
        product.setMission(mission);

        return product;
    }
}
