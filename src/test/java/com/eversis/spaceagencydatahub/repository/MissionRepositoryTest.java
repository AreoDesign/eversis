package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.entity.Mission;
import com.eversis.spaceagencydatahub.entity.Product;
import org.assertj.core.util.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import static com.eversis.spaceagencydatahub.repository.Utils.createMission;
import static com.eversis.spaceagencydatahub.repository.Utils.createProductForMission;
import static com.eversis.spaceagencydatahub.repository.Utils.removeMission;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MissionRepositoryTest {

    @Autowired
    private final MissionRepository missionRepository = null;

    @Autowired
    private final ProductRepository productRepository = null;

    private long dbMissionCnt;
    private Set<Mission> missions = Sets.newHashSet();
    private Set<Product> products = Sets.newHashSet();

    @Before
    public void init() {
        dbMissionCnt = missionRepository.count();
    }

    @After
    public void clean() {
        productRepository.deleteAll(products);
        missionRepository.deleteAll(missions);
    }

    @Test
    public void entityWriteReadDeleteTest() {
        //set up entities
        Mission mission = createMission("Capricorn", missions, missionRepository);
        Product product = createProductForMission(mission, products, productRepository);

        //test
        assertEquals(dbMissionCnt + 1, missionRepository.count());
        assertNotEquals(Instant.now(), missionRepository.findOne(Example.of(mission)).get().getStartDate());
        assertEquals(1, missionRepository.getProducts(mission).size());

        //same product test
        Set<Product> missionProducts = missionRepository.getProducts(mission);
        missionProducts.stream().forEach(r -> {
            assertEquals(product.getId(), r.getId());
            assertEquals(mission, r.getMission());
            assertEquals(product.getAcquisitionDate(), r.getAcquisitionDate());
            assertEquals(product.getFootprintLatitude(), r.getFootprintLatitude());
            assertEquals(product.getFootprintLongitude(), r.getFootprintLongitude());
            assertEquals(product.getFootprintAltitude(), r.getFootprintAltitude());
            assertEquals(product.getFootprintFourthCoordinate(), r.getFootprintFourthCoordinate());
            assertEquals(product.getPrice(), r.getPrice());
            assertEquals(product.getUrl(), r.getUrl());
            assertEquals(product.isActive(), r.isActive());
            assertEquals(product.getDeactivationDate(), r.getDeactivationDate());
        });

        removeMission(mission, missionRepository);
        assertFalse(missionRepository.findById(mission.getName()).get().isActive());
    }

    @Test
    public void lightWriteReadTest() throws InterruptedException {
        //set up entity
        Mission mission = Mission
                .builder("Calypso")
                .build();

        //save in db
        mission = missionRepository.save(mission);

        missions.add(mission);

        //test
        assertEquals(dbMissionCnt + 1, missionRepository.count());
        assertNotEquals(Instant.now(), missionRepository.findOne(Example.of(mission)).get().getStartDate());
    }

    @Test
    public void timeConsistencyTest() {
        LocalDateTime created = LocalDateTime.now(ZoneId.of("Europe/Warsaw")).truncatedTo(ChronoUnit.SECONDS);

        Instant startDate = Instant.now().truncatedTo(ChronoUnit.SECONDS);

        LocalDateTime startDatePolishTime = startDate.atZone(ZoneId.of("Europe/Warsaw")).toLocalDateTime();

        assertEquals(created, startDatePolishTime);
    }

}