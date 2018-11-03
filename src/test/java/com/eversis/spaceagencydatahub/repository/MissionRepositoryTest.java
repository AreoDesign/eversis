package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.dictionary.ImageType;
import com.eversis.spaceagencydatahub.entity.Mission;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MissionRepositoryTest {

    @Autowired
    private final MissionRepository missionRepository = null;

    @Test
    public void fullWriteReadTest() throws InterruptedException {
        final int dbMissionCnt = missionRepository.findAll().size();

        //set up entity
        Mission mission = Mission
                .builder("Calypso")
                .imageType(ImageType.HYPERSPECTRAL)
                .startDate(Instant.now())
                .endDate(Instant.now().plus(365, ChronoUnit.DAYS))
                .build();

        //save in db
        mission = missionRepository.save(mission);

        //test
        Assert.assertEquals(dbMissionCnt + 1, missionRepository.findAll().size());
        Thread.sleep(100);
        Assert.assertNotEquals(Instant.now(), missionRepository.findOne(Example.of(mission)).get().getStartDate());

        //clean after test
        missionRepository.delete(mission);
    }

    @Test
    public void lightWriteReadTest() throws InterruptedException {
        final int dbMissionCnt = missionRepository.findAll().size();

        //set up entity
        Mission mission = Mission
                .builder("Calypso")
                .build();

        //save in db
        mission = missionRepository.save(mission);

        //test
        Assert.assertEquals(dbMissionCnt + 1, missionRepository.findAll().size());
        Thread.sleep(100);
        Assert.assertNotEquals(Instant.now(), missionRepository.findOne(Example.of(mission)).get().getStartDate());

        //clean after test
        missionRepository.delete(mission);
    }

    @Test
    public void timeConsistencyTest() {
        LocalDateTime created = LocalDateTime.now(ZoneId.of("Europe/Warsaw")).truncatedTo(ChronoUnit.SECONDS);

        Instant startDate = Instant.now().truncatedTo(ChronoUnit.SECONDS);

        LocalDateTime startDatePolishTime = startDate.atZone(ZoneId.of("Europe/Warsaw")).toLocalDateTime();

        Assert.assertEquals(created, startDatePolishTime);
    }
}